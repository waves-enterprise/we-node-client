package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.http.PermitDataEntryDto
import com.wavesplatform.we.sdk.node.client.http.PermitDataEntryDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.PermitDataEntryDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.tx.DataTx

data class DataTxDto(
    override val id: String,
    override val type: Int = TxType.DATA.code,
    val senderPublicKey: String,
    val author: String,
    val authorPublicKey: String,
    val data: List<PermitDataEntryDto>,
    override val timestamp: Long,
    val fee: Long,
    val feeAssetId: String? = null,
    val proofs: List<String>? = null,
    val sender: String,
    val version: Int,
) : TxDto {
    companion object {
        @JvmStatic
        fun DataTx.toDto(): DataTxDto =
            DataTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                author = authorAddress.asBase58String(),
                authorPublicKey = authorPublicKey.asBase58String(),
                data = data.map { it.toDto() },
                timestamp = timestamp.utcTimestampMillis,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun DataTxDto.toDomain(): DataTx =
            DataTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                authorAddress = Address.fromBase58(author),
                authorPublicKey = PublicKey.fromBase58(authorPublicKey),
                data = data.map { it.toDomain() },
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                fee = Fee(fee),
                feeAssetId = feeAssetId?.let { FeeAssetId.fromBase58(it) },
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: DataTxDto): DataTx =
            tx.toDomain()
    }
}
