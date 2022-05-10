package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.Attachment
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.tx.TransferTx

data class TransferTxDto(
    override val id: String,
    override val type: Int = TxType.TRANSFER.code,
    val senderPublicKey: String,
    val assetId: String? = null,
    val feeAssetId: String? = null,
    override val timestamp: Long,
    val amount: Long,
    val fee: Long,
    val recipient: String,
    val attachment: String? = null,
    val atomicBadge: AtomicBadgeDto? = null,
    val proofs: List<String>? = null,
    val sender: String,
    val version: Int,
) : TxDto, AtomicInnerTxDto {
    companion object {
        @JvmStatic
        fun TransferTx.toDto(): TransferTxDto =
            TransferTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                assetId = assetId?.asBase58String(),
                feeAssetId = feeAssetId?.asBase58String(),
                timestamp = timestamp.utcTimestampMillis,
                amount = amount.value,
                fee = fee.value,
                recipient = recipient.asBase58String(),
                attachment = attachment?.asBase58String(),
                atomicBadge = atomicBadge?.toDto(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun TransferTxDto.toDomain(): TransferTx =
            TransferTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                assetId = assetId?.let { AssetId.fromBase58(it) },
                feeAssetId = feeAssetId?.let { FeeAssetId.fromBase58(it) },
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                amount = Amount(amount),
                fee = Fee(fee),
                recipient = Address.fromBase58(recipient),
                attachment = attachment?.let { Attachment.fromBase58(it) },
                atomicBadge = atomicBadge?.toDomain(),
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDtoInternal(tx: TransferTx): TransferTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: TransferTxDto): TransferTx =
            tx.toDomain()
    }
}
