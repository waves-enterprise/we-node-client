package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
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
import com.wavesplatform.we.sdk.node.client.http.TransferDto
import com.wavesplatform.we.sdk.node.client.http.TransferDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.TransferDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.tx.MassTransferTx

data class MassTransferTxDto(
    override val id: String,
    override val type: Int = TxType.MASS_TRANSFER.code,
    val senderPublicKey: String,
    val assetId: String? = null,
    val transfers: List<TransferDto>,
    override val timestamp: Long,
    val fee: Long,
    val attachment: String? = null,
    val feeAssetId: String? = null,
    val proofs: List<String>? = null,
    val sender: String,
    val version: Int,
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
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )
    }
}
