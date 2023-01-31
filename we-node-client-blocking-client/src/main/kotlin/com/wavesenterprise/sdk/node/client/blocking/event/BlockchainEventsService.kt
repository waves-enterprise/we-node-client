package com.wavesenterprise.sdk.node.client.blocking.event

import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest

interface BlockchainEventsService {
    fun events(request: SubscribeOnRequest): BlockchainEventsIterator
}
