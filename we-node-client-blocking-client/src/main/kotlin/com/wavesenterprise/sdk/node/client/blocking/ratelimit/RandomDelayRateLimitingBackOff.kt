package com.wavesenterprise.sdk.node.client.blocking.ratelimit

import kotlin.random.Random

class RandomDelayRateLimitingBackOff(
    private val minWaitMs: Long,
    private val maxWaitMs: Long,
    private val maxWaitTotalMs: Long
) : RateLimitingBackOff {

    override fun start() = RandomDelayRateLimitingBackOffExecution()

    inner class RandomDelayRateLimitingBackOffExecution : RateLimitingBackOffExecution {

        private var totalWaitMs: Long = 0

        override fun nextBackOff(): Long {
            val nextWait = when (maxWaitTotalMs - totalWaitMs) {
                in 0 until minWaitMs -> RateLimitingBackOff.STOP
                in minWaitMs..maxWaitMs -> maxWaitTotalMs - totalWaitMs
                else -> Random.nextLong(from = minWaitMs, until = maxWaitMs + 1)
            }
            totalWaitMs += nextWait
            return nextWait
        }
    }
}
