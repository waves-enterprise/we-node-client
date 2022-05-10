package com.wavesplatform.we.sdk.node.client.event

data class EventsFilter(
    val type: FilterType,
    val filter: Filter,
)
