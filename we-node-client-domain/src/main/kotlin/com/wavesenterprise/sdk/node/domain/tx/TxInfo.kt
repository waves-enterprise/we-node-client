package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Height

data class TxInfo(
    val height: Height,
    val tx: Tx,
)
