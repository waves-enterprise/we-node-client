package com.wavesenterprise.sdk.node.client.blocking.privacy.event

import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest

interface PrivacyEventsService {
    fun events(request: SubscribeOnRequest): PrivacyEventsIterator
}
