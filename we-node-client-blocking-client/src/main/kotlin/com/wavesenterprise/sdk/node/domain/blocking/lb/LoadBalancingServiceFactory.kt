package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.blocking.address.AddressService
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
        createService(TxService::class.java)

    override fun contractService(): ContractService =
        createService(ContractService::class.java)

    override fun addressService(): AddressService =
        createService(AddressService::class.java)

    override fun nodeInfoService(): NodeInfoService =
        createService(NodeInfoService::class.java)

    override fun privacyService(): PrivacyService =
        createService(PrivacyService::class.java)

    @Suppress("UNCHECKED_CAST")
    private fun <T> createService(clazz: Class<T>): T =
        Proxy.newProxyInstance(
            clazz::class.java.classLoader,
            arrayOf(clazz::class.java),
            LoadBalancingNodeServiceHandler(
                strategy = strategy,
                circuitBreaker = circuitBreaker,
                retryStrategy = retryStrategy,
            ),
        ) as T
}
