package com.wavesenterprise.sdk.node.domain.reactor.privacy.event

import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.event.BlockchainEventsDsl
import com.wavesenterprise.sdk.node.domain.event.EventsFilterContext
import com.wavesenterprise.sdk.node.domain.event.EventsFilterContextImpl
import com.wavesenterprise.sdk.node.domain.event.StartFrom
import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest
import com.wavesenterprise.sdk.node.domain.privacy.event.PrivacyEvent
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
