package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.transaction.protobuf.transfer.TransferTransaction
import com.wavesenterprise.transaction.protobuf.transfer.atomicBadgeOrNull
import com.wavesenterprise.transaction.protobuf.transfer.feeAssetIdOrNull
import com.wavesenterprise.transaction.protobuf.transfer.transferTransaction
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AssetIdMapper.bytesValue
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AtomicBadgeMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AttachmentMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.FeeAssetIdMapper.bytesValue
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesplatform.we.sdk.node.client.grpc.mapper.PublicKeyMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.SignatureMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.Util.setIfNotNull
import com.wavesplatform.we.sdk.node.client.tx.TransferTx

object TransferTxMapper {
    @JvmStatic
    fun TransferTx.dto(): TransferTransaction =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(tx: TransferTx): TransferTransaction =
        transferTransaction {
            id = tx.id.byteString()
            senderPublicKey = tx.senderPublicKey.byteString()
            setIfNotNull(this::assetId, tx.assetId) { it.bytesValue() }
            setIfNotNull(this::feeAssetId, tx.feeAssetId) { it.bytesValue() }
            timestamp = tx.timestamp.utcTimestampMillis
            amount = tx.amount.value
            fee = tx.fee.value
            recipient = tx.recipient.byteString()
            setIfNotNull(this::attachment, tx.attachment) { it.byteString() }
            setIfNotNull(this::atomicBadge, tx.atomicBadge) { it.dto() }
            tx.proofs?.also { domainProofs: List<Signature> ->
                proofs += domainProofs.map { it.byteString() }
            }
            senderAddress = tx.senderAddress.byteString()
        }

    @JvmStatic
    fun TransferTransaction.domain(version: TxVersion): TransferTx =
        domainInternal(this, version)

    @JvmStatic
    internal fun domainInternal(tx: TransferTransaction, version: TxVersion): TransferTx =
        TransferTx(
            id = TxId(tx.id.byteArray()),
            senderPublicKey = PublicKey(tx.senderPublicKey.toByteArray()),
            assetId = tx.assetId?.let { AssetId(it.byteArray()) },
            feeAssetId = tx.feeAssetIdOrNull?.let {
                FeeAssetId.fromByteArray(it.byteArray())
            },
            timestamp = Timestamp.fromUtcTimestamp(tx.timestamp),
            amount = Amount(tx.amount),
            fee = Fee(tx.fee),
            recipient = Address(tx.recipient.byteArray()),
            atomicBadge = tx.atomicBadgeOrNull?.domain(),
            proofs = tx.proofsList?.let { dtoProofs ->
                dtoProofs.map {
                    Signature(it.byteArray())
                }
            },
            senderAddress = Address(tx.senderAddress.byteArray()),
            version = version,
        )
}
