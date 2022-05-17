package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Height

data class TxInfo(
    val height: Height,
    val tx: Tx,
)
