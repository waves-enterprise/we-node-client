package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.ReissueTx

data class ReissueTxDto(
    override val id: String,
    override val type: Int = TxType.REISSUE.code,
    val chainId: Byte,
    val senderPublicKey: String,
    val assetId: String?,
    val quantity: Long,
    val reissuable: Boolean,
    val fee: Long,
    override val timestamp: Long,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto {
    companion object {
        @JvmStatic
        fun ReissueTx.toDto(): ReissueTxDto =
            ReissueTxDto(
                id = id.asBase58String(),
                chainId = chainId.value,
                senderPublicKey = senderPublicKey.asBase58String(),
                assetId = assetId?.asBase58String(),
                quantity = quantity.value,
                reissuable = reissuable,
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun ReissueTxDto.toDomain(): ReissueTx =
            ReissueTx(
                id = TxId.fromBase58(id),
                chainId = ChainId(chainId),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                assetId = assetId?.let { AssetId.fromBase58(it) },
                quantity = Quantity(quantity),
                reissuable = reissuable,
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: ReissueTxDto): ReissueTx =
            tx.toDomain()
    }
}
