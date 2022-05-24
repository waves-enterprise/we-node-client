package com.wavesenterprise.sdk.node.domain.coroutines.event

import com.wavesenterprise.sdk.node.domain.event.BlockchainEvent
import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest
import kotlinx.coroutines.flow.Flow

interface BlockchainEventsService {
    fun events(request: SubscribeOnRequest): Flow<BlockchainEvent>
}
