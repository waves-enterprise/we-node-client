package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.AssetIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.BurnTx
import com.wavesenterprise.transaction.protobuf.assets.BurnTransaction
import com.wavesenterprise.transaction.protobuf.assets.burnTransaction

object BurnTxMapper {
    @JvmStatic
    fun BurnTx.dto(): BurnTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: BurnTx): BurnTransaction =
        burnTransaction {
            id = tx.id.byteString()
            chainId = tx.chainId.value.toInt()
            senderPublicKey = tx.senderPublicKey.byteString()
            setIfNotNull(this::assetId, tx.assetId) { it.byteString() }
            amount = tx.amount.value
            fee = tx.fee.value
            timestamp = tx.timestamp.utcTimestampMillis
            tx.proofs?.also { domainProofs: List<Signature> ->
                proofs += domainProofs.map { it.byteString() }
            }
            senderAddress = tx.senderAddress.byteString()
        }

    @JvmStatic
    fun BurnTransaction.domain(version: TxVersion): BurnTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: BurnTransaction, version: TxVersion): BurnTx =
        BurnTx(
            id = TxId(tx.id.byteArray()),
            chainId = ChainId(tx.chainId.toByte()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            assetId = tx.assetId?.let {
                AssetId.fromByteArray(it.byteArray())
            },
            amount = Amount(tx.amount),
            fee = Fee(tx.fee),
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            proofs = tx.proofsList?.let { dtoProofs ->
                dtoProofs.map {
                    Signature(it.byteArray())
                }
            },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
