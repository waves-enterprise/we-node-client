package com.wavesenterprise.sdk.node.domain.blocking.ratelimit

import com.wavesenterprise.sdk.node.domain.blocking.ratelimit.RateLimitingBackOff.Companion.STOP
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.number.IsCloseTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

class RandomDelayRateLimitingBackOffTest {

    @Test
    fun `should return one fixed backoff`() {
        val minWaitMs = 1000L
        val backOff = RandomDelayRateLimitingBackOff(minWaitMs = minWaitMs, maxWaitMs = 1000, maxWaitTotalMs = 1000)
        val execution = backOff.start()
        assertEquals(minWaitMs, execution.nextBackOff())
        assertEquals(STOP, execution.nextBackOff())
    }

    @RepeatedTest(10)
    fun `should return random backoff and not exceed total wait time`() {
        val minWaitMs = 1000L
        val maxWaitMs = 3000L
        val maxWaitTotalMs = 10000L
        val backOff = RandomDelayRateLimitingBackOff(
            minWaitMs = minWaitMs,
            maxWaitMs = maxWaitMs,
            maxWaitTotalMs = maxWaitTotalMs,
        )
        val execution = backOff.start()

        var totalWaitTime = 0L
        var nextBackOff = execution.nextBackOff()
        while (nextBackOff != STOP) {
            assertTrue(nextBackOff in minWaitMs until maxWaitMs)
            totalWaitTime += nextBackOff
            nextBackOff = execution.nextBackOff()
        }

        assertThat(totalWaitTime.toDouble(), IsCloseTo(maxWaitTotalMs.toDouble(), minWaitMs.toDouble()))
    }
}
