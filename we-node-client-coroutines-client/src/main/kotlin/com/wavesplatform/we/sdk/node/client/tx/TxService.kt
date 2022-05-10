package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.sign.SignRequest

interface TxService {
    suspend fun <T : Tx> sign(request: SignRequest<T>): Tx
    suspend fun <T : Tx> signAndBroadcast(request: SignRequest<T>): Tx
    suspend fun <T : Tx> broadcast(tx: T): T
}
