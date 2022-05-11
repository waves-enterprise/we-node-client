package com.wavesplatform.we.sdk.node.client.reactor.event

import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.event.BlockchainEvent
import com.wavesplatform.we.sdk.node.client.event.BlockchainEventsDsl
import com.wavesplatform.we.sdk.node.client.event.EventsFilterContext
import com.wavesplatform.we.sdk.node.client.event.EventsFilterContextImpl
import com.wavesplatform.we.sdk.node.client.event.StartFrom
import com.wavesplatform.we.sdk.node.client.event.SubscribeOnRequest
import reactor.core.publisher.Flux

@BlockchainEventsDsl
fun BlockchainEventsService.fromGenesis(filtersBuilder: EventsFilterContext.() -> Unit = {}): Flux<BlockchainEvent> =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Genesis,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )

@BlockchainEventsDsl
fun BlockchainEventsService.fromBlock(signature: Signature, filtersBuilder: EventsFilterContext.() -> Unit = {}): Flux<BlockchainEvent> =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.BlockSignature(signature),
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )

@BlockchainEventsDsl
fun BlockchainEventsService.fromCurrent(filtersBuilder: EventsFilterContext.() -> Unit = {}): Flux<BlockchainEvent> =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Current,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )
