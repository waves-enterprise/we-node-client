package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory

class LbServiceFactoryBuilder {
    private var nodesResolver: NodesResolver? = null
    private var strategy: LoadBalanceStrategy? = null
    private var retryStrategy: RetryStrategy? = null
    private var circuitBreaker: CircuitBreaker? = null
    private var circuitBreakerProperties: CircuitBreakerProperties? = null
    private var nodeCredentials: NodeCredentials? = null

    fun nodesResolver(nodesResolver: NodesResolver): LbServiceFactoryBuilder =
        this.apply {
            this.nodesResolver = nodesResolver
        }

    fun strategy(strategy: LoadBalanceStrategy): LbServiceFactoryBuilder =
        this.apply {
            this.strategy = strategy
        }

    fun retryStrategy(retryStrategy: RetryStrategy): LbServiceFactoryBuilder =
        this.apply {
            this.retryStrategy = retryStrategy
        }

    fun circuitBreaker(circuitBreaker: CircuitBreaker): LbServiceFactoryBuilder =
        this.apply {
            this.circuitBreaker = circuitBreaker
        }

    /**
     * Builds [NodeBlockingServiceFactory] implementation with load balancing logic.
     * It will balance between passed nodeBlockingServiceFactories.
     * @param nodeAliasedServiceFactories map of name to [NodeBlockingServiceFactory]
     * @return NodeBlockingServiceFactory implementation with load balancing logic ([LoadBalancingServiceFactory])
     */
    fun build(
        nodeAliasedServiceFactories: Map<NodeIdentity, NodeBlockingServiceFactory>,
    ): LoadBalancingServiceFactory {
        val circuitBreakerProperties = circuitBreakerProperties ?: CircuitBreakerProperties()
        val nodeServiceFactoryWrappers = nodeAliasedServiceFactories.map {
            DefaultNodeServiceFactoryWrapper(
                nodeBlockingServiceFactory = it.value,
                name = it.key.nodeAlias,
                nodeCredentials = it.key.credentials,
            )
        }
        val nodeCircuitBreakers = nodeAliasedServiceFactories.map {
            it.key.nodeAlias to DefaultNodeCircuitBreaker(
                sequentialErrorCount = circuitBreakerProperties.sequentialErrorCount,
                breakUntil = circuitBreakerProperties.breakUntil,
            )
        }.toMap()
        val actualCircuitBreaker = circuitBreaker ?: DefaultCircuitBreaker(
            circuitBreakerProperties = circuitBreakerProperties,
            nodeCircuitBreakers = nodeCircuitBreakers,
        )
        val actualNodesResolver = nodesResolver ?: DefaultNodesResolver(
            nodeServiceFactoryWrappers = nodeServiceFactoryWrappers,
            circuitBreaker = actualCircuitBreaker,
        )
        val actualStrategy = strategy ?: DefaultLoadBalanceStrategy(
            nodesResolver = actualNodesResolver,
        )
        val actualRetryStrategy = retryStrategy ?: DefaultRetryStrategy()
        return LoadBalancingServiceFactory(
            strategy = actualStrategy,
            retryStrategy = actualRetryStrategy,
            circuitBreaker = actualCircuitBreaker,
        )
    }

    companion object {
        @JvmStatic
        fun builder() = LbServiceFactoryBuilder()
    }
}
