package com.wavesenterprise.sdk.node.domain.blocking.lb

import java.time.OffsetDateTime

interface NodeCircuitBreaker {
    var sequentialErrorCount: Long
    var breakUntil: OffsetDateTime

    fun invocationFailed(minDelay: Long, maxDelay: Long): Boolean
    fun tryReturnIntoRotation(): Boolean
    fun status(): CircuitBreakerStatus
}

enum class CircuitBreakerStatus {
    CLOSED,
    OPEN,
    ;
}
