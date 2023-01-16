package com.wavesenterprise.sdk.node.domain.blocking.ratelimit

import com.wavesenterprise.sdk.node.domain.blocking.ratelimit.exception.TooManyRequestsException

class DefaultRateLimiter(
    private val strategy: RateLimitingStrategy,
    private val backOff: RateLimitingBackOff,
) : RateLimiter {

    override fun waitIfNeeded() {
        val backOffExecution = backOff.start()
        while (strategy.isLimitExceeded()) {
            val nextBackOff = backOffExecution.nextBackOff()
            if (nextBackOff == RateLimitingBackOff.STOP) {
                throw TooManyRequestsException()
            } else {
                Thread.sleep(nextBackOff)
            }
        }
    }
}
