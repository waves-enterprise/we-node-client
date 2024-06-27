package com.wavesenterprise.sdk.node.client.blocking.privacy.event

import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.event.BlockchainEventsDsl
import com.wavesenterprise.sdk.node.domain.event.EventsFilterContext
import com.wavesenterprise.sdk.node.domain.event.EventsFilterContextImpl
import com.wavesenterprise.sdk.node.domain.event.StartFrom
import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest

@BlockchainEventsDsl
fun PrivacyEventsService.fromGenesis(filtersBuilder: EventsFilterContext.() -> Unit = {}): PrivacyEventsIterator =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Genesis,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        ),
    )

@BlockchainEventsDsl
fun PrivacyEventsService.fromBlock(
    signature: Signature,
    filtersBuilder: EventsFilterContext.() -> Unit = {
    }
): PrivacyEventsIterator =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.BlockSignature(signature),
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        ),
    )

@BlockchainEventsDsl
fun PrivacyEventsService.fromCurrent(filtersBuilder: EventsFilterContext.() -> Unit = {}): PrivacyEventsIterator =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Current,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        ),
    )
