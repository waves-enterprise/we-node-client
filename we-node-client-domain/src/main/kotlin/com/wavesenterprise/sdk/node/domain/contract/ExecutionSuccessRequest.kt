package com.wavesenterprise.sdk.node.domain.contract

import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.TxId

data class ExecutionSuccessRequest(
    val txId: TxId,
    val results: List<DataEntry>
)
