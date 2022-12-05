package com.wavesenterprise.sdk.node.client.feign.node

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.domain.node.NodeConfig

class FeignNodeInfoService(
    private val weNodeInfoService: WeNodeInfoServiceApiFeign,
) : NodeInfoService {

    override fun nodeConfig(): NodeConfig {
        weNodeInfoService.getNodeConfig()
        TODO("Not yet implemented")
    }

    override fun getNodeOwner(): NodeOwner =
        weNodeInfoService.getNodeOwnerAddress().run {
            NodeOwner(
                address = Address.fromBase58(address),
                publicKey = PublicKey.fromBase58(publicKey),
            )
        }
}
