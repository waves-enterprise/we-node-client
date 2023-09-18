package com.wavesenterprise.sdk.node.client.blocking.ratelimit

interface RateLimitingStrategy {
    fun isLimitExceeded(): Boolean
}
