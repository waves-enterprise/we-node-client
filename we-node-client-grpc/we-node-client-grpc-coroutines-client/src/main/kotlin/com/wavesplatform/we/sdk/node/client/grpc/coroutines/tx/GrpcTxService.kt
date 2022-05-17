package com.wavesplatform.we.sdk.node.client.grpc.coroutines.tx

import com.google.protobuf.Empty
import com.wavesenterprise.protobuf.service.transaction.TransactionPublicServiceGrpcKt.TransactionPublicServiceCoroutineStub
import com.wavesenterprise.protobuf.service.util.transactionInfoRequest
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.coroutines.tx.TxService
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.TxInfoMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.TxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.TxMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.UtxSizeMapper.domain
import com.wavesplatform.we.sdk.node.client.sign.SignRequest
import com.wavesplatform.we.sdk.node.client.tx.Tx
import com.wavesplatform.we.sdk.node.client.tx.TxInfo
import com.wavesplatform.we.sdk.node.client.tx.UtxSize
import io.grpc.ManagedChannel
import kotlinx.coroutines.flow.first

class GrpcTxService(
    private val channel: ManagedChannel,
) : TxService, AutoCloseable {
    val stub: TransactionPublicServiceCoroutineStub = TransactionPublicServiceCoroutineStub(channel)

    override suspend fun <T : Tx> sign(request: SignRequest<T>): Tx {
        TODO("Not yet implemented")
    }

    override suspend fun <T : Tx> signAndBroadcast(request: SignRequest<T>): Tx {
        TODO("Not yet implemented")
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Tx> broadcast(tx: T): T {
        val resultTx = stub.broadcast(tx.dto()).domain()
        return resultTx as? T ?: error("Invalid return type ${resultTx.javaClass.simpleName}")
    }

    override suspend fun utx(): List<Tx> {
        TODO("Not yet implemented")
    }

    override suspend fun utxInfo(): UtxSize =
        stub.utxInfo(Empty.getDefaultInstance())
            .first()
            .domain()

    override suspend fun txInfo(txId: TxId): TxInfo =
        stub.transactionInfo(
            transactionInfoRequest {
                this.txId = txId.asBase58String() // todo: ask why tdId is string
            }
        ).domain()

    override fun close() {
        channel.shutdown()
    }
}
