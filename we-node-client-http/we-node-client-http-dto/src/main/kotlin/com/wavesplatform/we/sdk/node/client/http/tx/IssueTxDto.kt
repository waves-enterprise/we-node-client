package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.ChainId
import com.wavesplatform.we.sdk.node.client.Decimals
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.IssueTxDescription
import com.wavesplatform.we.sdk.node.client.IssueTxName
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Quantity
import com.wavesplatform.we.sdk.node.client.Script
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.IssueTx

data class IssueTxDto(
    override val id: String,
    override val type: Int = TxType.ISSUE.code,
    val chainId: Byte,
    val senderPublicKey: String,
    val name: String,
    val description: String,
    val quantity: Long,
    val decimals: Byte,
    val reissuable: Boolean,
    val fee: Long,
    override val timestamp: Long,
    val script: String?,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
) : TxDto {
    companion object {
        @JvmStatic
        fun IssueTx.toDto(): IssueTxDto =
            IssueTxDto(
                id = id.asBase58String(),
                chainId = chainId.value,
                senderPublicKey = senderPublicKey.asBase58String(),
                name = name.asBase58String(),
                description = description.asBase58String(),
                quantity = quantity.value,
                decimals = decimals.value,
                reissuable = reissuable,
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                script = script?.asBase64String(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun IssueTxDto.toDomain(): IssueTx =
            IssueTx(
                id = TxId.fromBase58(id),
                chainId = ChainId(chainId),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                name = IssueTxName.fromBase58(name),
                description = IssueTxDescription.fromBase58(description),
                quantity = Quantity(quantity),
                decimals = Decimals(decimals),
                reissuable = reissuable,
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                script = script?.let { Script.fromBase64(it) },
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: IssueTxDto): IssueTx =
            tx.toDomain()
    }
}
