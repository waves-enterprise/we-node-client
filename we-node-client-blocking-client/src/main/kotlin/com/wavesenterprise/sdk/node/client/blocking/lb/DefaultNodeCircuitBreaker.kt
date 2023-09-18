package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.lb.CircuitBreakerStatus.CLOSED
import com.wavesenterprise.sdk.node.client.blocking.lb.CircuitBreakerStatus.OPEN
import java.time.OffsetDateTime
import kotlin.math.min
import kotlin.math.pow

class DefaultNodeCircuitBreaker(
    override var sequentialErrorCount: Long = 0L,
    override var breakUntil: OffsetDateTime = OffsetDateTime.MIN,
) : NodeCircuitBreaker {

    override fun invocationFailed(minDelay: Long, maxDelay: Long): Boolean =
        synchronized(this) {
            val now = OffsetDateTime.now()
            when {
                now.isAfter(breakUntil) -> {
                    sequentialErrorCount++
                    breakUntil = now.plusSeconds(
                        min(
                            maxDelay.toFloat(),
                            minDelay.toFloat().pow(sequentialErrorCount.toFloat()),
                        ).toLong()
                    )
                    true
                }
                else -> false
            }
        }

    override fun tryReturnIntoRotation(): Boolean =
        synchronized(this) {
            when (sequentialErrorCount) {
                0L -> false
                else -> {
                    sequentialErrorCount = 0
                    true
                }
            }
        }

    override fun status(): CircuitBreakerStatus =
        if (sequentialErrorCount == 0L || OffsetDateTime.now().isAfter(breakUntil)) CLOSED
        else OPEN
}
