package com.wavesplatform.we.sdk.node.client.reactor.privacy.event

import com.wavesplatform.we.sdk.node.client.event.SubscribeOnRequest
import com.wavesplatform.we.sdk.node.client.privacy.event.PrivacyEvent
import reactor.core.publisher.Flux

interface PrivacyEventsService {
    fun events(request: SubscribeOnRequest): Flux<PrivacyEvent>
}
