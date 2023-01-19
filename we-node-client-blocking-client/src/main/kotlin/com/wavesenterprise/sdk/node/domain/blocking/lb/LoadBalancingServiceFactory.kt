package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.blocking.address.AddressService
import com.wavesenterprise.sdk.node.domain.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.domain.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.domain.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService
import java.lang.reflect.Proxy

class LoadBalancingServiceFactory(
    private val strategy: LoadBalanceStrategy,
    private val retryStrategy: RetryStrategy,
    private val circuitBreaker: CircuitBreaker,
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
                fnServiceResolver = fnServiceResolver
            ),
        ) as T
}
