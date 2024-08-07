package com.wavesenterprise.sdk.node.domain.contract

import com.wavesenterprise.sdk.node.domain.TxId

data class ExecutionErrorRequest(
    val txId: TxId,
    val message: String,
    val code: ErrorCode,
) {
    enum class ErrorCode {
        FATAL_ERROR,
        RECOVERABLE_ERROR,
    }
}
