package com.wavesenterprise.sdk.node.client.coroutines.node

import com.wavesenterprise.sdk.node.domain.node.NodeConfig

interface NodeInfoService {
    suspend fun nodeConfig(): NodeConfig
}
