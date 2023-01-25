package com.wavesenterprise.sdk.node.client.blocking.ratelimit

interface RateLimitingBackOff {

    fun start(): RateLimitingBackOffExecution

    companion object {
        const val STOP = -1L
    }
}
