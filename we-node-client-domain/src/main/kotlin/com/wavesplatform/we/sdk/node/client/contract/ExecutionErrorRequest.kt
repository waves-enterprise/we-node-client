package com.wavesplatform.we.sdk.node.client.contract

import com.wavesplatform.we.sdk.node.client.TxId

data class ExecutionErrorRequest(
    val txId: TxId,
    val message: String,
    val code: Int
)
