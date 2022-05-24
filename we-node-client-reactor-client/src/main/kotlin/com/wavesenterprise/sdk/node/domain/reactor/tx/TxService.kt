package com.wavesenterprise.sdk.node.domain.reactor.tx

import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UtxSize
import reactor.core.publisher.Mono

interface TxService {
    fun <T : Tx> sign(request: SignRequest<T>): Mono<Tx>
    fun <T : Tx> signAndBroadcast(request: SignRequest<T>): Mono<Tx>
    fun <T : Tx> broadcast(tx: T): Mono<T>
    fun utx(): Mono<List<Tx>>
    fun utxInfo(): Mono<UtxSize>
    fun txInfo(txId: TxId): Mono<TxInfo>
}
