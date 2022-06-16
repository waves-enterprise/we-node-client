package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.TxCount

data class UtxSize(
    val txCount: TxCount,
    val size: DataSize,
)
