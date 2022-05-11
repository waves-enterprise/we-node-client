package com.wavesplatform.we.sdk.node.client.coroutines.event

import com.wavesplatform.we.sdk.node.client.event.BlockchainEvent
import com.wavesplatform.we.sdk.node.client.event.SubscribeOnRequest
import kotlinx.coroutines.flow.Flow

interface BlockchainEventsService {
    fun events(request: SubscribeOnRequest): Flow<BlockchainEvent>
}
