package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.exception.NodeInternalServerErrorException
import com.wavesenterprise.sdk.node.exception.NodeServiceUnavailableException
import com.wavesenterprise.sdk.node.exception.specific.PolicyItemDataIsMissingException

class DefaultRetryStrategy : RetryStrategy {
    override fun isRetryable(ex: Throwable): Boolean =
        when (ex) {
            is NodeInternalServerErrorException -> true
            is NodeServiceUnavailableException -> true
            is PolicyItemDataIsMissingException -> true
            else -> false
        }
}
