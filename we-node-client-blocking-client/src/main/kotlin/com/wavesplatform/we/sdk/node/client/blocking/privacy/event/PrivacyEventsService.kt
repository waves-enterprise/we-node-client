package com.wavesplatform.we.sdk.node.client.blocking.privacy.event

import com.wavesplatform.we.sdk.node.client.event.SubscribeOnRequest

interface PrivacyEventsService {
    fun events(request: SubscribeOnRequest): PrivacyEventsIterator
}
