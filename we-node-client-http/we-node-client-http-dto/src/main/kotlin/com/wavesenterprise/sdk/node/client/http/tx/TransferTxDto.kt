package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Attachment
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.TransferTx

data class TransferTxDto(
    override val id: String,
    override val type: Int = TxType.TRANSFER.code,
    val senderPublicKey: String,
    val assetId: String?,
    val feeAssetId: String?,
    override val timestamp: Long,
    val amount: Long,
    val fee: Long,
    val recipient: String,
    val attachment: String?,
    val atomicBadge: AtomicBadgeDto?,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto, AtomicInnerTxDto, AtomicSignInnerTxDto {
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
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDtoInternal(tx: TransferTx): TransferTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: TransferTxDto): TransferTx =
            tx.toDomain()
    }
}
