package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.blocking.lb.exception.NoNodesToHandleRequestException
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory
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

    private val logger = LoggerFactory.getLogger(LoadBalancingNodeServiceHandler::class.java)

    override fun invoke(
        proxy: Any,
        method: Method,
        args: Array<out Any>?,
    ): Any {
        val methodName = method.name
        logger.debug("Invoking ${proxy.javaClass} method $methodName")
        val clientsWithArgs = strategy.resolve(method, args)
        if (clientsWithArgs.isEmpty())
            throw NoNodesToHandleRequestException(
                "No LoadBalancingServiceFactory is configured to handle request $methodName"
            )
        logger.debug("Got ${clientsWithArgs.size} nodes to handle invocation of method $methodName")
        return clientsWithArgs.dispatch(method)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> List<ClientWithArgs>.dispatch(
        method: Method,
    ): T {
        var lastException: Throwable? = null
        fun retry(e: Throwable) {
            logger.warn("Error: ${e.javaClass.simpleName} : ${e.message}, retrying operation")
            lastException = e
        }
        forEachIndexed { index, nodeWithArgs ->
            val nodeServiceFactoryWrapper = nodeWithArgs.client
            val args = nodeWithArgs.methodCallArgs
            logger.debug("Attempt ${index + 1}/$size handling ${method.name}")
            try {
                val service = resolveService(nodeServiceFactoryWrapper)
                return (
                    if (args == null) {
                        method.invoke(service)
                    } else {
                        method.invoke(service, *args)
                    } as T
                    ).also {
                    logger.debug("Invocation successful")
                    circuitBreaker.tryReturnIntoRotation(nodeServiceFactoryWrapper.name)
                }
            } catch (ex: Exception) {
                val resultingEx = when (ex) {
                    is InvocationTargetException -> ex.targetException
                    else -> ex
                }
                if (retryStrategy.isRetryable(resultingEx)) {
                    circuitBreaker.invocationFailed(nodeServiceFactoryWrapper.name, index)
                    retry(resultingEx)
                } else {
                    throw resultingEx
                }
                logger.debug("Invocation failed: ${resultingEx.message}", resultingEx)
            }
        }

        if (lastException != null) {
            val e = lastException!!
            logger.error("Exception executing method ${method.name}, error ${e.javaClass.simpleName}: ${e.message}", e)
            throw e
        } else {
            val error = "No nodes for executing method ${method.name}"
            logger.error(error)
            throw IllegalStateException(error)
        }
    }

    private fun resolveService(client: NodeServiceFactoryWrapper) = fnServiceResolver(client.nodeBlockingServiceFactory)
}
