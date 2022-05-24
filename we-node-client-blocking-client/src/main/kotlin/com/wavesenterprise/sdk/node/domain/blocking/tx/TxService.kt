package com.wavesenterprise.sdk.node.domain.blocking.tx

import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UtxSize

interface TxService {
    fun <T : Tx> sign(request: SignRequest<T>): T
    fun <T : Tx> signAndBroadcast(request: SignRequest<T>): Tx
    fun <T : Tx> broadcast(tx: T): T
    fun utxInfo(): UtxSize
    fun txInfo(txId: TxId): TxInfo
}
