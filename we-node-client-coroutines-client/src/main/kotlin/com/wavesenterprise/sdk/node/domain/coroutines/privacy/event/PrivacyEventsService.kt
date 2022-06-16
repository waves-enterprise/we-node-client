package com.wavesenterprise.sdk.node.domain.coroutines.privacy.event

import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest
import com.wavesenterprise.sdk.node.domain.privacy.event.PrivacyEvent
import kotlinx.coroutines.flow.Flow

interface PrivacyEventsService {
    fun events(request: SubscribeOnRequest): Flow<PrivacyEvent>
}
