package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.ChainId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Quantity
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.ReissueTx

data class ReissueTxDto(
    override val id: String,
    override val type: Int = TxType.REISSUE.code,
    val chainId: Byte,
    val senderPublicKey: String,
    val assetId: String? = null,
    val quantity: Long,
    val reissuable: Boolean,
    val fee: Long,
    override val timestamp: Long,
    val proofs: List<String>? = null,
    val sender: String,
    val version: Int,
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
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )
    }
}
