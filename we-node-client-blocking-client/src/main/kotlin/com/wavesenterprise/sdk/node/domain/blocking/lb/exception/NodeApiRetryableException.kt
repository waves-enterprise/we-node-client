package com.wavesenterprise.sdk.node.domain.blocking.lb.exception

class NodeApiRetryableException(
    override val cause: Exception,
    val quarantine: Boolean = true
) : RuntimeException(cause)
