package com.wavesenterprise.sdk.node.client.grpc.blocking.tx

import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.domain.tx.TxInfo
import com.wavesenterprise.sdk.node.domain.tx.UtxSize
import io.grpc.Channel
import java.util.Optional

@Suppress("UnusedPrivateProperty")
class TxGrpcBlockingService(
    private val channel: Channel,
) : TxService, AutoCloseable {
    override fun <T : Tx> sign(request: SignRequest<T>): T {
        TODO("Not yet implemented")
    }

    override fun <T : Tx> signAndBroadcast(request: SignRequest<T>): T {
        TODO("Not yet implemented")
    }

    override fun <T : Tx> broadcast(tx: T): T {
        TODO("Not yet implemented")
    }

    override fun utxInfo(): List<Tx> {
        TODO("Not yet implemented")
    }

    override fun utxSize(): UtxSize {
        TODO("Not yet implemented")
    }

    override fun txInfo(txId: TxId): Optional<TxInfo> {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
