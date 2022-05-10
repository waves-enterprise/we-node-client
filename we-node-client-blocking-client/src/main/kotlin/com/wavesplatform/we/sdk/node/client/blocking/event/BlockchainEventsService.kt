package com.wavesplatform.we.sdk.node.client.blocking.event

import com.wavesplatform.we.sdk.node.client.event.SubscribeOnRequest

interface BlockchainEventsService {
    fun events(request: SubscribeOnRequest): BlockchainEventsIterator
}
