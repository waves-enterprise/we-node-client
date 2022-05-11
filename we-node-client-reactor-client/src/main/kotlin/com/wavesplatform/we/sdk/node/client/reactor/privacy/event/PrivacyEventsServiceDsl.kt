package com.wavesplatform.we.sdk.node.client.reactor.privacy.event

import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.event.BlockchainEventsDsl
import com.wavesplatform.we.sdk.node.client.event.EventsFilterContext
import com.wavesplatform.we.sdk.node.client.event.EventsFilterContextImpl
import com.wavesplatform.we.sdk.node.client.event.StartFrom
import com.wavesplatform.we.sdk.node.client.event.SubscribeOnRequest
import com.wavesplatform.we.sdk.node.client.privacy.event.PrivacyEvent
import reactor.core.publisher.Flux

@BlockchainEventsDsl
fun PrivacyEventsService.fromGenesis(filtersBuilder: EventsFilterContext.() -> Unit = {}): Flux<PrivacyEvent> =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Genesis,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )

@BlockchainEventsDsl
fun PrivacyEventsService.fromBlock(signature: Signature, filtersBuilder: EventsFilterContext.() -> Unit = {}): Flux<PrivacyEvent> =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.BlockSignature(signature),
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )

@BlockchainEventsDsl
fun PrivacyEventsService.fromCurrent(filtersBuilder: EventsFilterContext.() -> Unit = {}): Flux<PrivacyEvent> =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Current,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )
