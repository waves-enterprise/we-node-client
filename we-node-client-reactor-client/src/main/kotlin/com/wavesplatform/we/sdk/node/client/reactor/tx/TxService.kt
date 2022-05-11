package com.wavesplatform.we.sdk.node.client.reactor.tx

import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.sign.SignRequest
import com.wavesplatform.we.sdk.node.client.tx.Tx
import com.wavesplatform.we.sdk.node.client.tx.TxInfo
import com.wavesplatform.we.sdk.node.client.tx.UtxSize
import reactor.core.publisher.Mono

interface TxService {
    fun <T : Tx> sign(request: SignRequest<T>): Mono<Tx>
    fun <T : Tx> signAndBroadcast(request: SignRequest<T>): Mono<Tx>
    fun <T : Tx> broadcast(tx: T): Mono<T>
    fun utx(): Mono<List<Tx>>
    fun utxInfo(): Mono<UtxSize>
    fun txInfo(txId: TxId): Mono<TxInfo>
}
