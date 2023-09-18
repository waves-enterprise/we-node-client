package com.wavesenterprise.sdk.node.exception.specific

import com.wavesenterprise.sdk.node.exception.EventReceivingException

class BlockNotFoundSubscriptionException(
    val signature: String? = null,
    cause: Throwable? = null,
) : EventReceivingException(
    message = signature?.run { "Block not found, signature: $this" },
    cause = cause,
)
