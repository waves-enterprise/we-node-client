package com.wavesenterprise.sdk.node.domain.blocking.lb

import java.time.OffsetDateTime

data class CircuitBreakerProperties(
    val minDelay: Long = 2L,
    val maxDelay: Long = 300L,
    var sequentialErrorCount: Long = 1L,
    var breakUntil: OffsetDateTime = OffsetDateTime.MIN,
)
