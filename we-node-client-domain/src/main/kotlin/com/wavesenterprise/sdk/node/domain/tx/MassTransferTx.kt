package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Attachment
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.Transfer
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.sign.FieldInfo

data class MassTransferTx(
    override val id: TxId,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 1)
    val senderPublicKey: PublicKey,
    @FieldInfo(required = false, sinceVersion = 1, bytesPosition = 2)
    val assetId: AssetId? = null,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 3)
    val transfers: List<Transfer>,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 4)
    override val timestamp: Timestamp,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 5)
    val fee: Fee,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 6)
    val attachment: Attachment? = null,
    @FieldInfo(required = false, sinceVersion = 2, bytesPosition = 7)
    val feeAssetId: FeeAssetId? = null,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx {
    override fun withId(id: TxId): Tx = copy(id = id)

    override fun withProof(proof: Signature): Tx = copy(proofs = proofs?.plus(proof) ?: listOf(proof))

    override fun withSenderAddress(senderAddress: Address): Tx = copy(senderAddress = senderAddress)
}
