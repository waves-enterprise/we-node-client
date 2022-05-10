package com.wavesplatform.we.sdk.node.client.node

interface NodeInfoService {
    suspend fun nodeConfig(): NodeConfig
}
