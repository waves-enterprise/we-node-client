package com.wavesenterprise.sdk.node.client.feign.factory

import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.client.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.blocking.event.BlockchainEventsService
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.client.blocking.pki.PkiService
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.client.blocking.util.NodeUtilsService
import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import com.wavesenterprise.sdk.node.client.feign.address.FeignAddressService
import com.wavesenterprise.sdk.node.client.feign.address.WeAddressServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.blocks.FeignBlocksService
import com.wavesenterprise.sdk.node.client.feign.blocks.WeBlocksServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.contract.FeignContractService
import com.wavesenterprise.sdk.node.client.feign.contract.WeContractServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.node.FeignNodeInfoService
import com.wavesenterprise.sdk.node.client.feign.node.WeNodeInfoServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.pki.FeignPkiService
import com.wavesenterprise.sdk.node.client.feign.pki.WePkiServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.privacy.FeignPrivacyService
import com.wavesenterprise.sdk.node.client.feign.privacy.WePrivacyServiceApiFeign
import com.wavesenterprise.sdk.node.client.feign.tx.FeignTxService
import com.wavesenterprise.sdk.node.client.feign.tx.WeTxApiFeign
import com.wavesenterprise.sdk.node.client.feign.util.FeignNodeUtilsService
import com.wavesenterprise.sdk.node.client.feign.util.WeUtilsServiceApiFeign

class FeignNodeServiceFactory(
    private val params: FeignNodeClientParams,
) : NodeBlockingServiceFactory {

    override fun txService(): TxService {
        val weTxApi = FeignWeApiFactory.createClient(
            clientClass = WeTxApiFeign::class.java,
            loggerName = TxService::class.java.name,
            feignProperties = params,
        )
        return FeignTxService(weTxApi)
    }

    override fun contractService(): ContractService {
        val weContractServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeContractServiceApiFeign::class.java,
            loggerName = ContractService::class.java.name,
            feignProperties = params,
        )
        return FeignContractService(weContractServiceApiFeign)
    }

    override fun addressService(): AddressService {
        val weAddressServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeAddressServiceApiFeign::class.java,
            loggerName = AddressService::class.java.name,
            feignProperties = params,
        )
        return FeignAddressService(weAddressServiceApiFeign)
    }

    override fun nodeInfoService(): NodeInfoService {
        val weNodeInfoServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeNodeInfoServiceApiFeign::class.java,
            loggerName = NodeInfoService::class.java.name,
            feignProperties = params,
        )
        return FeignNodeInfoService(weNodeInfoServiceApiFeign)
    }

    override fun privacyService(): PrivacyService {
        val wePrivacyServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WePrivacyServiceApiFeign::class.java,
            loggerName = PrivacyService::class.java.name,
            feignProperties = params,
        )
        return FeignPrivacyService(wePrivacyServiceApiFeign)
    }

    override fun blocksService(): BlocksService {
        val weBlocksServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeBlocksServiceApiFeign::class.java,
            loggerName = BlocksService::class.java.name,
            feignProperties = params,
        )
        return FeignBlocksService(weBlocksServiceApiFeign)
    }

    override fun blockchainEventsService(): BlockchainEventsService {
        TODO("Not yet implemented")
    }

    override fun nodeUtilsService(): NodeUtilsService {
        val weBlocksServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeUtilsServiceApiFeign::class.java,
            loggerName = NodeUtilsService::class.java.name,
            feignProperties = params,
        )
        return FeignNodeUtilsService(weBlocksServiceApiFeign)
    }

    override fun pkiService(): PkiService {
        val wePkiServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WePkiServiceApiFeign::class.java,
            loggerName = PkiService::class.java.name,
            feignProperties = params,
        )
        return FeignPkiService(wePkiServiceApiFeign)
    }
}
