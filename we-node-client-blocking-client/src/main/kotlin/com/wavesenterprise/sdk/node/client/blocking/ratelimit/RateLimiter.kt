package com.wavesenterprise.sdk.node.client.blocking.ratelimit

interface RateLimiter {
    fun waitIfNeeded(): Unit
}
