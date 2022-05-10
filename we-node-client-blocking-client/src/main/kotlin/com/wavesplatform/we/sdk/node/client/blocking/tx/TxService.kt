package com.wavesplatform.we.sdk.node.client.blocking.tx

import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.sign.SignRequest
import com.wavesplatform.we.sdk.node.client.tx.Tx
import com.wavesplatform.we.sdk.node.client.tx.TxInfo
import com.wavesplatform.we.sdk.node.client.tx.UtxSize

interface TxService {
    fun <T : Tx> sign(request: SignRequest<T>): T
    fun <T : Tx> signAndBroadcast(request: SignRequest<T>): Tx
    fun <T : Tx> broadcast(tx: T): T
    fun utxInfo(): UtxSize
    fun txInfo(txId: TxId): TxInfo
}
