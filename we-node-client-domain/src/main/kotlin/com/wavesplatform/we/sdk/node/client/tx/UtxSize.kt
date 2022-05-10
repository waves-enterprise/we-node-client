package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.DataSize
import com.wavesplatform.we.sdk.node.client.TxCount

data class UtxSize(
    val txCount: TxCount,
    val size: DataSize,
)
