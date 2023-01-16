package com.wavesenterprise.sdk.node.domain.blocking.lb

fun interface RetryStrategy {
    fun isRetryable(ex: Throwable): Boolean
}
