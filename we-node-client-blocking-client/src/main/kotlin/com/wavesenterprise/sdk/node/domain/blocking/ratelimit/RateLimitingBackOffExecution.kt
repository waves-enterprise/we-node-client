package com.wavesenterprise.sdk.node.domain.blocking.ratelimit

interface RateLimitingBackOffExecution {

    fun nextBackOff(): Long
}
