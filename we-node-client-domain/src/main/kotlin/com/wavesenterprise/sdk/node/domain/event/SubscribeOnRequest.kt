package com.wavesenterprise.sdk.node.domain.event

data class SubscribeOnRequest(
    val startFrom: StartFrom,
    val filters: List<EventsFilter>,
)
