package com.wavesenterprise.sdk.node.domain.blocking.ratelimit

interface RateLimiter {
    fun waitIfNeeded(): Unit
}
