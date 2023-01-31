package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.credentials.NodeCredentialsProvider
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory

class LbServiceFactoryBuilder {
    private var nodesResolver: NodesResolver? = null
    private var strategy: LoadBalanceStrategy? = null
    private var retryStrategy: RetryStrategy? = null
    private var circuitBreaker: CircuitBreaker? = null
    private var circuitBreakerProperties: CircuitBreakerProperties? = null
    private var nodeCredentialsProvider: NodeCredentialsProvider? = null

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

    fun nodeCredentialsProvider(nodeCredentialsProvider: NodeCredentialsProvider) =
        this.apply {
            this.nodeCredentialsProvider = nodeCredentialsProvider
        }

    /**
     * Builds [NodeBlockingServiceFactory] implementation with load balancing logic.
     * It will balance between passed nodeBlockingServiceFactories.
     * @param nodeAliasedServiceFactories map of node alias to [NodeBlockingServiceFactory]
     * @return NodeBlockingServiceFactory implementation with load balancing logic ([LoadBalancingServiceFactory])
     */
    fun build(
        nodeAliasedServiceFactories: Map<String, NodeBlockingServiceFactory>,
    ): LoadBalancingServiceFactory {
        val actualNodeCredentialsProvider = requireNotNull(nodeCredentialsProvider) {
            "NodeCredentialsProvider can not be null"
        }
        val circuitBreakerProperties = circuitBreakerProperties ?: CircuitBreakerProperties()
        val nodeServiceFactoryWrappers = nodeAliasedServiceFactories.map {
            DefaultNodeServiceFactoryWrapper(
                nodeBlockingServiceFactory = it.value,
                name = it.key,
            )
        }
        val nodeCircuitBreakers = nodeAliasedServiceFactories.map {
            it.key to DefaultNodeCircuitBreaker(
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
            nodeCredentialsProvider = actualNodeCredentialsProvider,
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
