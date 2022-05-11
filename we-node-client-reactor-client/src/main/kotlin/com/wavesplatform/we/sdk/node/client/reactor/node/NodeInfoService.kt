package com.wavesplatform.we.sdk.node.client.reactor.node

import com.wavesplatform.we.sdk.node.client.node.NodeConfig
import reactor.core.publisher.Mono

interface NodeInfoService {
    fun nodeConfig(): Mono<NodeConfig>
}
