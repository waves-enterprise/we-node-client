package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Reissuable
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.sign.FieldInfo

data class ReissueTx(
    override val id: TxId,
    @FieldInfo(required = true, sinceVersion = 2, bytesPosition = 1)
    val chainId: ChainId,
    @FieldInfo(required = true, sinceVersion = 2, bytesPosition = 2)
    val senderPublicKey: PublicKey,
    @FieldInfo(required = true, sinceVersion = 2, bytesPosition = 3)
    val assetId: AssetId? = null,
    @FieldInfo(required = true, sinceVersion = 2, bytesPosition = 4)
    val quantity: Quantity,
    @FieldInfo(required = true, sinceVersion = 2, bytesPosition = 5)
    val reissuable: Reissuable,
    @FieldInfo(required = true, sinceVersion = 2, bytesPosition = 6)
    val fee: Fee,
    @FieldInfo(required = true, sinceVersion = 2, bytesPosition = 7)
    override val timestamp: Timestamp,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx {
    override fun withId(id: TxId): Tx = copy(id = id)

    override fun withProof(proof: Signature): Tx = copy(proofs = proofs?.plus(proof) ?: listOf(proof))

    override fun withSenderAddress(senderAddress: Address): Tx = copy(senderAddress = senderAddress)
}
