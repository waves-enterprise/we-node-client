package com.wavesplatform.we.sdk.node.client.event

data class SubscribeOnRequest(
    val startFrom: StartFrom,
    val filters: List<EventsFilter>,
)
