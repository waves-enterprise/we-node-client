package com.wavesenterprise.sdk.node.client.blocking.ratelimit

interface RateLimitingBackOffExecution {

    fun nextBackOff(): Long
}
