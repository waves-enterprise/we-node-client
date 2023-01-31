package com.wavesenterprise.sdk.node.client.reactor.event

import com.wavesenterprise.sdk.node.domain.event.BlockchainEvent
import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest
import reactor.core.publisher.Flux

interface BlockchainEventsService {
    fun events(request: SubscribeOnRequest): Flux<BlockchainEvent>
}
