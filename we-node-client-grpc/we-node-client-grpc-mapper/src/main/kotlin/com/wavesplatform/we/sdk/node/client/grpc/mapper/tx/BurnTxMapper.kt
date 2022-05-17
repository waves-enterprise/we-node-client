package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.assets.BurnTransaction
import com.wavesenterprise.transaction.protobuf.assets.burnTransaction
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.ChainId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AssetIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesplatform.we.sdk.node.client.tx.BurnTx

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
