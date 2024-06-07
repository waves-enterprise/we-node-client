package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.AtomicInnerTxMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.AtomicInnerTxMapper.dto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.transaction.protobuf.AtomicTransaction
import com.wavesenterprise.transaction.protobuf.atomicTransaction
import com.wavesenterprise.transaction.protobuf.minerOrNull

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
            miner = tx.minerOrNull?.let { Address(it.value.byteArray()) },
            txs = tx.transactionsList.map { it.domain(version) },
            timestamp = Timestamp(tx.timestamp),
            proofs = tx.proofsList?.map { Signature(it.byteArray()) },
            senderAddress = Address(tx.senderAddress.byteArray()),
            fee = Fee(0),
            version = version,
        )
}
