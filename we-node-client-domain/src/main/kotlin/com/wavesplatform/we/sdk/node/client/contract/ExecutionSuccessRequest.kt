package com.wavesplatform.we.sdk.node.client.contract

import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.TxId

data class ExecutionSuccessRequest(
    val txId: TxId,
    val results: List<DataEntry>
)
