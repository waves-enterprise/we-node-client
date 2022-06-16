package com.wavesenterprise.sdk.node.domain.http.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Decimals
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.IssueTxDescription
import com.wavesenterprise.sdk.node.domain.IssueTxName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.IssueTx

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
    override val height: Long? = null,
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
