package com.wavesenterprise.sdk.node.client.feign.node

import com.wavesenterprise.sdk.node.client.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.client.http.node.NodeOwnerDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.node.NodeConfig

class FeignNodeInfoService(
    private val weNodeInfoServiceApiFeign: WeNodeInfoServiceApiFeign,
) : NodeInfoService {

    override fun nodeConfig(): NodeConfig {
        weNodeInfoServiceApiFeign.getNodeConfig()
        TODO("Not yet implemented")
    }

    override fun getNodeOwner(): NodeOwner =
        weNodeInfoServiceApiFeign.getNodeOwnerAddress().toDomain()
}
