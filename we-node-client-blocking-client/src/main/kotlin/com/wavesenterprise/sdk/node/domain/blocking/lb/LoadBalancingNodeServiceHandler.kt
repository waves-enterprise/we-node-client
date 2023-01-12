package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.blocking.lb.exception.NoNodesToHandleRequestException
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.exception.NodeErrorInfoHolder
import org.slf4j.LoggerFactory
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class LoadBalancingNodeServiceHandler(
    private val strategy: LoadBalanceStrategy,
    private val circuitBreaker: CircuitBreaker,
    private val retryStrategy: RetryStrategy,
    private val fnServiceResolver: (NodeBlockingServiceFactory) -> (Any),
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
                    circuitBreaker.invocationFailed(nodeServiceFactoryWrapper.name, index)
                    retry(e)
                }
            }
            LOG.debug("Attempt ${index + 1}/$size handling ${method.name}")
            try {
                val service = resolveService(nodeServiceFactoryWrapper)
                return (
                    if (args == null) {
                        method.invoke(service)
                    } else {
                        method.invoke(service, *args)
                    } as T
                    ).also {
                    LOG.debug("Invocation successful")
                    circuitBreaker.tryReturnIntoRotation(nodeServiceFactoryWrapper.name)
                }
            } catch (ex: InvocationTargetException) {
                val e = ex.targetException
                LOG.debug("Invocation failed: ${e.message}", e)
                when (e) {
                    is NodeErrorInfoHolder -> {
                        if (retryStrategy.isRetryable(e as Exception))
                            onException(e)
                        else
                            LOG.debug(
                                "Returning error ${e.javaClass.simpleName}: ${e.message}, code ${e.nodeError?.error}"
                            )
                        throw e
                    }

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

    private fun resolveService(client: NodeServiceFactoryWrapper) = fnServiceResolver(client.nodeBlockingServiceFactory)
}
