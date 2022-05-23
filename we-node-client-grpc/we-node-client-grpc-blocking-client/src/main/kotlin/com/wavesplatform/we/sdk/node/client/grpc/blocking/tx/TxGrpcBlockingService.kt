package com.wavesplatform.we.sdk.node.client.grpc.blocking.tx

import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.blocking.tx.TxService
import com.wavesplatform.we.sdk.node.client.sign.SignRequest
import com.wavesplatform.we.sdk.node.client.tx.Tx
import com.wavesplatform.we.sdk.node.client.tx.TxInfo
import com.wavesplatform.we.sdk.node.client.tx.UtxSize
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
