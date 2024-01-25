package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.NodeName
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.sign.FieldInfo

data class RegisterNodeTx(
    override val id: TxId,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 1)
    val senderPublicKey: PublicKey,
    val target: Address,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 2)
    val targetPublicKey: PublicKey,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 3)
    val nodeName: NodeName?,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 4)
    val opType: OpType,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 5)
    override val timestamp: Timestamp,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 6)
    val fee: Fee,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx {
    override fun withId(id: TxId): Tx = copy(id = id)

    override fun withProof(proof: Signature): Tx = copy(proofs = proofs?.plus(proof) ?: listOf(proof))

    override fun withSenderAddress(senderAddress: Address): Tx = copy(senderAddress = senderAddress)
}
