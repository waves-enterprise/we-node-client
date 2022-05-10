package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.NodeName
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.http.OpTypeConstants.fromOpTypeDtoToDomain
import com.wavesplatform.we.sdk.node.client.http.OpTypeConstants.toDto
import com.wavesplatform.we.sdk.node.client.tx.RegisterNodeTx

data class RegisterNodeTxDto(
    override val id: String,
    override val type: Int = TxType.REGISTER_NODE.code,
    val senderPublicKey: String,
    val target: String,
    val targetPublicKey: String,
    val nodeName: String,
    val opType: String,
    override val timestamp: Long,
    val fee: Long,
    val proofs: List<String>? = null,
    val sender: String,
    val version: Int,
) : TxDto {
    companion object {
        @JvmStatic
        fun RegisterNodeTx.toDto(): RegisterNodeTxDto =
            RegisterNodeTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                target = target.asBase58String(),
                targetPublicKey = targetPublicKey.asBase58String(),
                nodeName = nodeName.value,
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
                target = Address.fromBase58(target),
                targetPublicKey = PublicKey.fromBase58(targetPublicKey),
                nodeName = NodeName(nodeName),
                opType = opType.fromOpTypeDtoToDomain(),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                fee = Fee(fee),
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )
    }
}
