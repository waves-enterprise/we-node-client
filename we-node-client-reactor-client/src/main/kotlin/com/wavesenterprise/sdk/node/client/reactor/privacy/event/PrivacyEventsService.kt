package com.wavesenterprise.sdk.node.client.reactor.privacy.event

import com.wavesenterprise.sdk.node.domain.event.SubscribeOnRequest
import com.wavesenterprise.sdk.node.domain.privacy.event.PrivacyEvent
import reactor.core.publisher.Flux

interface PrivacyEventsService {
    fun events(request: SubscribeOnRequest): Flux<PrivacyEvent>
}
