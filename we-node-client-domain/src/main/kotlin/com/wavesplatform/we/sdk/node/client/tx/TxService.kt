package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.TxId

interface TxService {
    fun broadcast(tx: Tx): Tx
    fun utxInfo(): UtxSize
    fun txInfo(txId: TxId): TxInfo
}
