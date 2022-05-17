package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion

sealed interface Tx {
    val id: TxId
    val timestamp: Timestamp
    val version: TxVersion
}
