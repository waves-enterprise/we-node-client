package com.wavesenterprise.sdk.node.domain.event

data class EventsFilter(
    val type: FilterType,
    val filter: Filter,
)
