package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.client.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.blocking.event.BlockchainEventsService
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.client.blocking.util.NodeUtilsService
import java.lang.reflect.Proxy

class LoadBalancingServiceFactory(
    private val strategy: LoadBalanceStrategy,
    private val retryStrategy: RetryStrategy,
    private val circuitBreaker: CircuitBreaker,
    private val recipientsCacheLoadBalancerPostInvokeHandler: RecipientsCacheLoadBalancerPostInvokeHandler,
    private val privacyDataNodesCacheLoadBalancerPostInvokeHandler: PrivacyDataNodesCacheLoadBalancerPostInvokeHandler,
) : NodeBlockingServiceFactory {

    override fun txService() =
        createService(TxService::class.java) {
            it.txService()
        }

    override fun contractService(): ContractService =
        createService(ContractService::class.java) {
            it.contractService()
        }

    override fun addressService(): AddressService =
        createService(AddressService::class.java) {
            it.addressService()
        }

    override fun nodeInfoService(): NodeInfoService =
        createService(NodeInfoService::class.java) {
            it.nodeInfoService()
        }

    override fun privacyService(): PrivacyService =
        createService(PrivacyService::class.java) {
            it.privacyService()
        }

    override fun blocksService(): BlocksService =
        createService(BlocksService::class.java) {
            it.blocksService()
        }

    override fun blockchainEventsService(): BlockchainEventsService =
        createService(BlockchainEventsService::class.java) {
            it.blockchainEventsService()
        }

    override fun nodeUtilsService(): NodeUtilsService =
        createService(NodeUtilsService::class.java) {
            it.nodeUtilsService()
        }

    @Suppress("UNCHECKED_CAST")
    private fun <T> createService(
        clazz: Class<T>,
        fnServiceResolver: (NodeBlockingServiceFactory) -> (Any),
    ): T =
        Proxy.newProxyInstance(
            clazz.classLoader,
            arrayOf(clazz),
            LoadBalancingNodeServiceHandler(
                strategy = strategy,
                circuitBreaker = circuitBreaker,
                retryStrategy = retryStrategy,
                fnServiceResolver = fnServiceResolver,
                loadBalancerPostInvokeHandlers = listOf(
                    recipientsCacheLoadBalancerPostInvokeHandler,
                    privacyDataNodesCacheLoadBalancerPostInvokeHandler,
                ),
            ),
        ) as T
}
