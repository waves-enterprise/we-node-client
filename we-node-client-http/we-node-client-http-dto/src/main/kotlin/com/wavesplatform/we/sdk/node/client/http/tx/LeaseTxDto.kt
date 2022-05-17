package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.http.tx.TxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.tx.LeaseTx

data class LeaseTxDto(
    override val id: String,
    override val type: Int = TxType.LEASE.code,
    val assetId: String?,
    val senderPublicKey: String,
    val recipient: String,
    val amount: Long,
    val fee: Long,
    override val timestamp: Long,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
) : TxDto {
    companion object {
        @JvmStatic
        fun LeaseTx.toDto(): LeaseTxDto =
            LeaseTxDto(
                id = id.asBase58String(),
                assetId = assetId?.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                recipient = recipient.asBase58String(),
                amount = amount.value,
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun LeaseTxDto.toDomain(): LeaseTx =
            LeaseTx(
                id = TxId.fromBase58(id),
                assetId = assetId?.let { AssetId.fromBase58(it) },
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                recipient = Address.fromBase58(recipient),
                amount = Amount(amount),
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: LeaseTxDto): LeaseTx =
            tx.toDomain()
    }
}
