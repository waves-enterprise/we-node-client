package com.wavesplatform.we.sdk.node.client.coroutines.privacy.event

import com.wavesplatform.we.sdk.node.client.event.SubscribeOnRequest
import com.wavesplatform.we.sdk.node.client.privacy.event.PrivacyEvent
import kotlinx.coroutines.flow.Flow

interface PrivacyEventsService {
    fun events(request: SubscribeOnRequest): Flow<PrivacyEvent>
}
