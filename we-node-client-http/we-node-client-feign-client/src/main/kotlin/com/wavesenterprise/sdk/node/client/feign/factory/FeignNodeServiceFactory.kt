package com.wavesenterprise.sdk.node.client.feign.factory

import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import com.wavesenterprise.sdk.node.client.feign.blocks.FeignBlocksService
import com.wavesenterprise.sdk.node.client.feign.blocks.WeBlocksServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.contract.FeignContractService
import com.wavesenterprise.sdk.node.client.feign.contract.WeContractServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.node.FeignAddressService
import com.wavesenterprise.sdk.node.client.feign.node.FeignNodeInfoService
import com.wavesenterprise.sdk.node.client.feign.node.WeAddressServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.node.WeNodeInfoServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.privacy.FeignPrivacyService
import com.wavesenterprise.sdk.node.client.feign.privacy.WePrivacyServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.tx.FeignTxService
import com.wavesenterprise.sdk.node.client.feign.tx.WeTxApiFeign
import com.wavesenterprise.sdk.node.domain.blocking.address.AddressService
import com.wavesenterprise.sdk.node.domain.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.domain.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.domain.blocking.privacy.PrivacyService
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

    override fun addressService(): AddressService {
        val weAddressServiceApiFeign = FeignWeApiFactory.createClient(
            WeAddressServiceApiFeign::class.java,
            params,
        )
        return FeignAddressService(weAddressServiceApiFeign)
    }

    override fun nodeInfoService(): NodeInfoService {
        val weNodeInfoServiceApiFeign = FeignWeApiFactory.createClient(
            WeNodeInfoServiceApiFeign::class.java,
            params,
        )
        return FeignNodeInfoService(weNodeInfoServiceApiFeign)
    }

    override fun privacyService(): PrivacyService {
        val wePrivacyServiceApiFeign = FeignWeApiFactory.createClient(
            WePrivacyServiceApiFeign::class.java,
            params,
        )
        return FeignPrivacyService(wePrivacyServiceApiFeign)
    }

    override fun blocksService(): BlocksService {
        val weBlocksServiceApiFeign = FeignWeApiFactory.createClient(
            WeBlocksServiceApiFeign::class.java,
            params,
        )
        return FeignBlocksService(weBlocksServiceApiFeign)
    }
}
