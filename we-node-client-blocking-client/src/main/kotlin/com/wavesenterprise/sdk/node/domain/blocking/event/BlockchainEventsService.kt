package com.wavesenterprise.sdk.node.domain.blocking.event

import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest

interface BlockchainEventsService {
    fun events(request: SubscribeOnRequest): com.wavesenterprise.sdk.node.domain.blocking.event.BlockchainEventsIterator
}
