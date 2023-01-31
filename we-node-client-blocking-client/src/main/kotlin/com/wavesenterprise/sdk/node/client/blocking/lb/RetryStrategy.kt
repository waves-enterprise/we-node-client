package com.wavesenterprise.sdk.node.client.blocking.lb

fun interface RetryStrategy {
    fun isRetryable(ex: Throwable): Boolean
}
