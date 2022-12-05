package com.wavesenterprise.sdk.node.domain.blocking.node

import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.node.NodeConfig

interface NodeInfoService {
    fun nodeConfig(): NodeConfig
    fun getNodeOwner(): NodeOwner
}
