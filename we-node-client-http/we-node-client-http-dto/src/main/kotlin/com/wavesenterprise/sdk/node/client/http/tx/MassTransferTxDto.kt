package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.client.http.TransferDto
import com.wavesenterprise.sdk.node.client.http.TransferDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.TransferDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
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
import com.wavesenterprise.sdk.node.domain.tx.MassTransferTx

data class MassTransferTxDto(
    override val id: String,
    override val type: Int = TxType.MASS_TRANSFER.code,
    val senderPublicKey: String,
    val assetId: String?,
    val transfers: List<TransferDto>,
    override val timestamp: Long,
    val fee: Long,
    val attachment: String?,
    val feeAssetId: String?,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto {
    companion object {
        @JvmStatic
        fun MassTransferTx.toDto(): MassTransferTxDto =
            MassTransferTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                assetId = assetId?.asBase58String(),
                transfers = transfers.map { it.toDto() },
                timestamp = timestamp.utcTimestampMillis,
                fee = fee.value,
                attachment = attachment?.asBase58String(),
                feeAssetId = feeAssetId?.asBase58String(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun MassTransferTxDto.toDomain(): MassTransferTx =
            MassTransferTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                assetId = assetId?.let { AssetId.fromBase58(it) },
                transfers = transfers.map { it.toDomain() },
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                fee = Fee(fee),
                attachment = attachment?.let { Attachment.fromBase58(it) },
                feeAssetId = feeAssetId?.let { FeeAssetId.fromBase58(it) },
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: MassTransferTxDto): MassTransferTx =
            tx.toDomain()
    }
}
