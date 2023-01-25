package com.wavesenterprise.sdk.node.client.blocking.tx

import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UtxSize
import java.util.Optional

interface TxService {
    fun <T : Tx> sign(request: SignRequest<T>): T
    fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T
    fun <T : Tx> broadcast(tx: T): T
    fun utxInfo(): UtxSize
    fun txInfo(txId: TxId): Optional<TxInfo>
}
