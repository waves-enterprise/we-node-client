package com.wavesenterprise.sdk.node.domain.contract

import com.wavesenterprise.sdk.node.domain.TxId

data class ExecutionErrorRequest(
    val txId: TxId,
    val message: String,
    val code: Int,
) {
    object ErrorCode {
        const val FATAL_ERROR = 0
        const val RECOVERABLE_ERROR = 1
    }
}
