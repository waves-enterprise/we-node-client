package com.wavesplatform.we.sdk.node.client.blocking.privacy.event

import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.event.BlockchainEventsDsl
import com.wavesplatform.we.sdk.node.client.event.EventsFilterContext
import com.wavesplatform.we.sdk.node.client.event.EventsFilterContextImpl
import com.wavesplatform.we.sdk.node.client.event.StartFrom
import com.wavesplatform.we.sdk.node.client.event.SubscribeOnRequest

@BlockchainEventsDsl
fun PrivacyEventsService.fromGenesis(filtersBuilder: EventsFilterContext.() -> Unit = {}): PrivacyEventsIterator =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Genesis,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )

@BlockchainEventsDsl
fun PrivacyEventsService.fromBlock(signature: Signature, filtersBuilder: EventsFilterContext.() -> Unit = {}): PrivacyEventsIterator =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.BlockSignature(signature),
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )

@BlockchainEventsDsl
fun PrivacyEventsService.fromCurrent(filtersBuilder: EventsFilterContext.() -> Unit = {}): PrivacyEventsIterator =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Current,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )
