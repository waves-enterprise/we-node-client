package com.wavesenterprise.sdk.node.client.blocking.privacy.event

import com.wavesenterprise.sdk.node.domain.privacy.event.PrivacyEvent

interface PrivacyEventsIterator : Iterator<PrivacyEvent>, AutoCloseable
