package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.AtomicTransaction
import com.wavesenterprise.transaction.protobuf.atomicTransaction
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.bytesValue
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.AtomicInnerTxMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.AtomicInnerTxMapper.dto
import com.wavesplatform.we.sdk.node.client.tx.AtomicTx

object AtomicTxMapper {
    @JvmStatic
    fun AtomicTx.dto(): AtomicTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: AtomicTx): AtomicTransaction =
        atomicTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            setIfNotNull(this::miner, tx.miner) { it.bytesValue() }
            transactions += tx.txs.map { it.dto() }
            timestamp = tx.timestamp.utcTimestampMillis
            tx.proofs?.also { domainProofs: List<Signature> ->
                proofs += domainProofs.map { it.byteString() }
            }
            senderAddress = tx.senderAddress.byteString()
        }

    @JvmStatic
    fun AtomicTransaction.domain(version: TxVersion): AtomicTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: AtomicTransaction, version: TxVersion): AtomicTx =
        AtomicTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            txs = tx.transactionsList.map { it.domain(version) },
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            proofs = tx.proofsList?.let { dtoProofs ->
                dtoProofs.map {
                    Signature(it.byteArray())
                }
            },
            senderAddress = Address(tx.senderAddress.byteArray()),
            fee = Fee(0),
            version = version,
        )
}
