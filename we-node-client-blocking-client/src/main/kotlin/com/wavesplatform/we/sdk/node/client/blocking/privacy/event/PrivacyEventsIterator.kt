package com.wavesplatform.we.sdk.node.client.blocking.privacy.event

import com.wavesplatform.we.sdk.node.client.privacy.event.PrivacyEvent

interface PrivacyEventsIterator : Iterator<PrivacyEvent>, AutoCloseable
