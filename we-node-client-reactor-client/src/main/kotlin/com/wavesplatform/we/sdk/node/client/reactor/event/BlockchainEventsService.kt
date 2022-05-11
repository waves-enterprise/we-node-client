package com.wavesplatform.we.sdk.node.client.reactor.event

import com.wavesplatform.we.sdk.node.client.event.BlockchainEvent
import com.wavesplatform.we.sdk.node.client.event.SubscribeOnRequest
import reactor.core.publisher.Flux

interface BlockchainEventsService {
    fun events(request: SubscribeOnRequest): Flux<BlockchainEvent>
}
