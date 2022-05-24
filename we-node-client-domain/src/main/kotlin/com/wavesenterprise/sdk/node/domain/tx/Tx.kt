package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion

sealed interface Tx {
    val id: TxId
    val timestamp: Timestamp
    val version: TxVersion
}
