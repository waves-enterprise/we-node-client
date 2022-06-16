package com.wavesenterprise.sdk.node.domain.blocking.privacy.event

import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest

interface PrivacyEventsService {
    fun events(request: SubscribeOnRequest): PrivacyEventsIterator
}
