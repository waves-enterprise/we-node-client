package com.wavesplatform.we.sdk.node.client.coroutines.tx

import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.sign.SignRequest
import com.wavesplatform.we.sdk.node.client.tx.Tx
import com.wavesplatform.we.sdk.node.client.tx.TxInfo
import com.wavesplatform.we.sdk.node.client.tx.UtxSize

interface TxService {
    suspend fun <T : Tx> sign(request: SignRequest<T>): T
    suspend fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T
    suspend fun <T : Tx> broadcast(tx: T): T
    suspend fun utx(): List<Tx>
    suspend fun utxInfo(): UtxSize
    suspend fun txInfo(txId: TxId): TxInfo
}
