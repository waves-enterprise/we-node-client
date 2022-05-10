package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.SponsorFeeTx

data class SponsorFeeTxDto(
    override val id: String,
    override val type: Int = TxType.SPONSOR_FEE.code,
    val senderPublicKey: String,
    val assetId: String? = null,
    val enabled: Boolean,
    val fee: Long,
    override val timestamp: Long,
    val proofs: List<String>? = null,
    val sender: String,
    val version: Int,
) : TxDto {
    companion object {
        @JvmStatic
        fun SponsorFeeTx.toDto(): SponsorFeeTxDto =
            SponsorFeeTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                assetId = assetId?.asBase58String(),
                enabled = enabled,
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun SponsorFeeTxDto.toDomain(): SponsorFeeTx =
            SponsorFeeTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                assetId = assetId?.let { AssetId.fromBase58(it) },
                enabled = enabled,
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: SponsorFeeTxDto): SponsorFeeTx =
            tx.toDomain()
    }
}
