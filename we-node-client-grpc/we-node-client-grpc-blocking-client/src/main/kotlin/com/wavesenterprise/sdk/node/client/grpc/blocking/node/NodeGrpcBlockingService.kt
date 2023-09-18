package com.wavesenterprise.sdk.node.client.grpc.blocking.node

import com.wavesenterprise.sdk.node.client.blocking.node.NodeInfoService
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.node.NodeConfig

class NodeGrpcBlockingService : NodeInfoService {
    override fun nodeConfig(): NodeConfig {
        TODO("Not yet implemented")
    }

    override fun getNodeOwner(): NodeOwner {
        TODO("Not yet implemented")
    }
}
