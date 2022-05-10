package com.wavesplatform.we.sdk.node.client.event

interface BlockchainEventsService {
    fun events(request: SubscribeOnRequest): BlockchainEventsIterator
}
