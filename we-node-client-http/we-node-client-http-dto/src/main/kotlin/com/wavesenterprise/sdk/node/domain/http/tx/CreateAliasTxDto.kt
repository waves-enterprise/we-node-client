package com.wavesenterprise.sdk.node.domain.http.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Alias
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.CreateAliasTx

data class CreateAliasTxDto(
    override val id: String,
    override val type: Int = TxType.CREATE_ALIAS.code,
    val senderPublicKey: String,
    val alias: String,
    val fee: Long,
    override val timestamp: Long,
    val feeAssetId: String?,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto {
    companion object {
        @JvmStatic
        fun CreateAliasTx.toDto(): CreateAliasTxDto =
            CreateAliasTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                alias = alias.value,
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                feeAssetId = feeAssetId?.asBase58String(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun CreateAliasTxDto.toDomain(): CreateAliasTx =
            CreateAliasTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                alias = Alias(alias),
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                feeAssetId = feeAssetId?.let { FeeAssetId.fromBase58(it) },
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: CreateAliasTxDto): CreateAliasTx =
            tx.toDomain()
    }
}
