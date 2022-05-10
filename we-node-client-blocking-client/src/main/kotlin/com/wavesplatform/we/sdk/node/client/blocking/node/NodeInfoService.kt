package com.wavesplatform.we.sdk.node.client.blocking.node

import com.wavesplatform.we.sdk.node.client.node.NodeConfig

interface NodeInfoService {
    fun nodeConfig(): NodeConfig
}
