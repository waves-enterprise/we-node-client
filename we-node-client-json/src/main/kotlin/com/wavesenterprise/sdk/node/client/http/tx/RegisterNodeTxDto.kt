package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.client.http.OpTypeConstants.fromOpTypeDtoToDomain
import com.wavesenterprise.sdk.node.client.http.OpTypeConstants.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.NodeName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.RegisterNodeTx

data class RegisterNodeTxDto(
    override val id: String,
    override val type: Int = TxType.REGISTER_NODE.code,
    val senderPublicKey: String,
    val targetPubKey: String,
    val nodeName: String?,
    val opType: String,
    override val timestamp: Long,
    val fee: Long,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto {
    companion object {
        @JvmStatic
        fun RegisterNodeTx.toDto(): RegisterNodeTxDto =
            RegisterNodeTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                targetPubKey = targetPublicKey.asBase58String(),
                nodeName = nodeName?.value,
                opType = opType.toDto(),
                timestamp = timestamp.utcTimestampMillis,
                fee = fee.value,
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun RegisterNodeTxDto.toDomain(): RegisterNodeTx =
            RegisterNodeTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                targetPublicKey = PublicKey.fromBase58(targetPubKey),
                nodeName = nodeName?.let { NodeName(it) },
                opType = opType.fromOpTypeDtoToDomain(),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                fee = Fee(fee),
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: RegisterNodeTxDto): RegisterNodeTx =
            tx.toDomain()
    }
}
