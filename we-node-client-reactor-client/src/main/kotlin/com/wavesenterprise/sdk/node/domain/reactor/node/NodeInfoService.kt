package com.wavesenterprise.sdk.node.domain.reactor.node

import com.wavesenterprise.sdk.node.domain.node.NodeConfig
import reactor.core.publisher.Mono

interface NodeInfoService {
    fun nodeConfig(): Mono<NodeConfig>
}
