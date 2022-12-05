package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.blocking.lb.exception.NoNodesToHandleRequestException
import com.wavesenterprise.sdk.node.domain.blocking.lb.exception.NodeApiRetryableException
import com.wavesenterprise.sdk.node.domain.blocking.lb.exception.TooManyRequestsException
import feign.FeignException
import org.slf4j.LoggerFactory
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class LoadBalancingNodeServiceHandler(
    private val strategy: LoadBalanceStrategy,
    private val minQuarantineDelay: Long,
    private val maxQuarantineDelay: Long,
) : InvocationHandler {

    private val LOG = LoggerFactory.getLogger(LoadBalancingNodeServiceHandler::class.java)

    override fun invoke(
        proxy: Any,
        method: Method,
        args: Array<out Any>?,
    ): Any {
        val methodName = method.name
        LOG.debug("Invoking ${proxy.javaClass} method $methodName")
        val clientsWithArgs = strategy.resolve(method, args)
        if (clientsWithArgs.isEmpty())
            throw NoNodesToHandleRequestException(
                "No LoadBalancingServiceFactory is configured to handle request $methodName"
            )
        LOG.debug("Got ${clientsWithArgs.size} nodes to handle invocation of method $methodName")
        return clientsWithArgs.dispatch(method)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> List<ClientWithArgs>.dispatch(
        method: Method,
    ): T {
        var lastException: Exception? = null
        val retry = { e: Exception ->
            LOG.warn("Error: ${e.javaClass.simpleName} : ${e.message}, retrying operation")
            lastException = e
        }

        forEachIndexed { index, nodeWithArgs ->
            val nodeServiceFactoryWrapper = nodeWithArgs.client
            val args = nodeWithArgs.methodCallArgs
            val onException: (e: Exception) -> Unit by lazy {
                { e: Exception ->
                    invocationFailed(nodeServiceFactoryWrapper, index)
                    retry(e)
                }
            }
            LOG.debug("Attempt ${index + 1}/$size handling ${method.name}")
            try {
                return (
                    if (args == null) {
                        method.invoke(nodeServiceFactoryWrapper)
                    } else {
                        method.invoke(nodeServiceFactoryWrapper, *args)
                    } as T
                    ).also {
                    LOG.debug("Invocation successful")
                    val sequentialErrorCountBefore = nodeServiceFactoryWrapper.sequentialErrorCount
                    if (nodeServiceFactoryWrapper.tryReturnIntoRotation()) {
                        LOG.info(
                            "Node with index $index, name ${nodeServiceFactoryWrapper.name}" +
                                " return into rotation, sequentialErrorCount before $sequentialErrorCountBefore",
                        )
                    }
                }
            } catch (ex: InvocationTargetException) {
                val e = ex.targetException
                LOG.debug("Invocation failed: ${e.message}", e)
                when (e) {
                    // Todo: add throwing of general exceptions (for gprs and http) and add their handling here
                    is NodeApiRetryableException -> if (e.quarantine) onException(e.cause) else retry(e.cause)
                    is FeignException -> when {
                        e.status().is5xxError() -> onException(e)
                        else -> {
                            LOG.debug("Returning error ${e.javaClass.simpleName}: ${e.message}, code ${e.status()}")
                            throw e
                        }
                    }

                    is TooManyRequestsException -> throw e
                    is Exception -> onException(e)
                }
            } catch (e: Exception) {
                LOG.warn("Invocation failed: ${e.message}", e)
                onException(e)
            }
        }

        if (lastException != null) {
            val e = lastException!!
            LOG.error("Exception executing method ${method.name}, error ${e.javaClass.simpleName}: ${e.message}", e)
            throw e
        } else {
            val error = "No nodes for executing method ${method.name}"
            LOG.error(error)
            throw IllegalStateException(error)
        }
    }

    private fun invocationFailed(nodeServiceFactoryWrapper: NodeServiceFactoryWrapper, index: Int) {
        with(nodeServiceFactoryWrapper) {
            LOG.warn(
                "Node with index $index, name $name will try to remove from rotation until $quarantineUntil," +
                    " sequentialErrorCount $sequentialErrorCount"
            )
            if (invocationFailed(minQuarantineDelay, maxQuarantineDelay)) {
                LOG.warn(
                    "Node with index $index, name $name successfully removed from rotation until $quarantineUntil," +
                        " sequentialErrorCount $sequentialErrorCount"
                )
            }
        }
        // TODO: Move quarantine logic
    }

    // TODO: Move to util
    private fun Int.is5xxError() = this / 500 == 1
}
