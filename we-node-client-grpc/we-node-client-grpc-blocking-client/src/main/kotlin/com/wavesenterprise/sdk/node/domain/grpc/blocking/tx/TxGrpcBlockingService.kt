package com.wavesenterprise.sdk.node.domain.grpc.blocking.tx

import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UtxSize
import io.grpc.ManagedChannel

class TxGrpcBlockingService(
    private val channel: ManagedChannel,
) : TxService, AutoCloseable {
    override fun <T : Tx> sign(request: SignRequest<T>): T {
        TODO("Not yet implemented")
    }

    override fun <T : Tx> signAndBroadcast(request: SignRequest<T>): Tx {
        TODO("Not yet implemented")
    }

    override fun <T : Tx> broadcast(tx: T): T {
        TODO("Not yet implemented")
    }

    override fun utxInfo(): UtxSize {
        TODO("Not yet implemented")
    }

    override fun txInfo(txId: TxId): TxInfo {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
