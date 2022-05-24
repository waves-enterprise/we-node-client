package com.wavesenterprise.sdk.node.domain.coroutines.node

import com.wavesenterprise.sdk.node.domain.node.NodeConfig

interface NodeInfoService {
    suspend fun nodeConfig(): NodeConfig
}
