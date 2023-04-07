package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.client.http.PermitDataEntryDto
import com.wavesenterprise.sdk.node.client.http.PermitDataEntryDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.PermitDataEntryDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.DataTx

data class DataTxDto(
    override val id: String,
    override val type: Int = TxType.DATA.code,
    val senderPublicKey: String,
    val author: String,
    val authorPublicKey: String,
    val data: List<PermitDataEntryDto>,
    override val timestamp: Long,
    val fee: Long,
    val feeAssetId: String?,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
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
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: DataTxDto): DataTx =
            tx.toDomain()
    }
}
