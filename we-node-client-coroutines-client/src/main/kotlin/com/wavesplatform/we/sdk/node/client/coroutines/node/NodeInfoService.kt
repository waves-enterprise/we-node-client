package com.wavesplatform.we.sdk.node.client.coroutines.node

import com.wavesplatform.we.sdk.node.client.node.NodeConfig

interface NodeInfoService {
    suspend fun nodeConfig(): NodeConfig
}
