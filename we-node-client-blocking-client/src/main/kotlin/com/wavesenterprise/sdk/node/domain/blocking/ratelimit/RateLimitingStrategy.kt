package com.wavesenterprise.sdk.node.domain.blocking.ratelimit

interface RateLimitingStrategy {
    fun isLimitExceeded(): Boolean
}
