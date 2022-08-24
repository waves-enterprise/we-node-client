package com.wavesenterprise.sdk.node.client.feign.factory

import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import com.wavesenterprise.sdk.node.client.feign.contract.FeignContractService
import com.wavesenterprise.sdk.node.client.feign.contract.WeContractServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.tx.FeignTxService
import com.wavesenterprise.sdk.node.client.feign.tx.WeTxApiFeign
import com.wavesenterprise.sdk.node.domain.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService

class FeignNodeServiceFactory(
    private val params: FeignNodeClientParams
) : NodeBlockingServiceFactory {

    override fun txService(): TxService {
        val weTxApi = FeignWeApiFactory.createClient(
            WeTxApiFeign::class.java,
            params,
        )
        return FeignTxService(weTxApi)
    }

    override fun contractService(): ContractService {
        val weContractServiceApiFeign = FeignWeApiFactory.createClient(
            WeContractServiceApiFeign::class.java,
            params,
        )
        return FeignContractService(weContractServiceApiFeign)
    }
}
