package com.wavesenterprise.sdk.node.domain.http.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.ScriptDescription
import com.wavesenterprise.sdk.node.domain.ScriptName
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.SetScriptTx

data class SetScriptTxDto(
    override val id: String,
    override val type: Int = TxType.SET_SCRIPT.code,
    val chainId: Byte,
    val senderPublicKey: String,
    val script: String?,
    val name: String,
    val description: String?,
    val fee: Long,
    override val timestamp: Long,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto {
    companion object {
        @JvmStatic
        fun SetScriptTx.toDto(): SetScriptTxDto =
            SetScriptTxDto(
                id = id.asBase58String(),
                chainId = chainId.value,
                senderPublicKey = senderPublicKey.asBase58String(),
                script = script?.asBase64String(),
                name = name.asBase58String(),
                description = description?.asBase58String(),
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun SetScriptTxDto.toDomain(): SetScriptTx =
            SetScriptTx(
                id = TxId.fromBase58(id),
                chainId = ChainId(chainId),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                script = script?.let { Script.fromBase64(it) },
                name = ScriptName.fromBase58(name),
                description = description?.let { ScriptDescription.fromBase58(description) },
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: SetScriptTxDto): SetScriptTx =
            tx.toDomain()
    }
}
