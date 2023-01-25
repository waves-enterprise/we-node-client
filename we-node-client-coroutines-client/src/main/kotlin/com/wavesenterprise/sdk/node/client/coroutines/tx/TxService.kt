package com.wavesenterprise.sdk.node.client.coroutines.tx

import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UtxSize

interface TxService {
    suspend fun <T : Tx> sign(request: SignRequest<T>): T
    suspend fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T
    suspend fun <T : Tx> broadcast(tx: T): T
    suspend fun utx(): List<Tx>
    suspend fun utxInfo(): UtxSize
    suspend fun txInfo(txId: TxId): TxInfo
}
