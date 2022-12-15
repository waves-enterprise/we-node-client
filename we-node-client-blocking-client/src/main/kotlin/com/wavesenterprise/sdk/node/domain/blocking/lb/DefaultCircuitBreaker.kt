package com.wavesenterprise.sdk.node.domain.blocking.lb

import org.slf4j.LoggerFactory

class DefaultCircuitBreaker(
    private val minDelay: Long,
    private val maxDelay: Long,
    override val nodeCircuitBreakers: Map<String, NodeCircuitBreaker>,
) : CircuitBreaker {

    private val LOG = LoggerFactory.getLogger(DefaultCircuitBreaker::class.java)

    override fun invocationFailed(nodeName: String, index: Int) {
        with(getCircuitBreakerHandler(nodeName)) {
            LOG.warn(
                "Node with index $index, name $nodeName will try " +
                    "to remove from rotation until $breakUntil," +
                    " sequentialErrorCount $sequentialErrorCount"
            )
            if (this.invocationFailed(minDelay, maxDelay)) {
                LOG.warn(
                    "Node with index $index, name $nodeName " +
                        "successfully removed from rotation until $breakUntil," +
                        " sequentialErrorCount $sequentialErrorCount"
                )
            }
        }
    }

    override fun tryReturnIntoRotation(nodeName: String) {
        val circuitBreakerHandler = getCircuitBreakerHandler(nodeName)
        circuitBreakerHandler.tryReturnIntoRotation()
    }

    override fun isClosed(
        nodeServiceFactoryWrapper: NodeServiceFactoryWrapper
    ): Boolean = nodeCircuitBreakers[nodeServiceFactoryWrapper.name] != null

    private fun getCircuitBreakerHandler(nodeName: String) =
        requireNotNull(nodeCircuitBreakers[nodeName]) {
            "NodeServiceFactoryWrapper with name: $nodeName not found"
        }
}
