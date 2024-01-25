package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ContractApiVersion
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.ValidationPolicy
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.sign.FieldInfo

data class UpdateContractTx(
    override val id: TxId,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 1)
    val senderPublicKey: PublicKey,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 2)
    val contractId: ContractId,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 3)
    val image: ContractImage,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 4)
    val imageHash: ContractImageHash,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 5)
    val fee: Fee,
    @FieldInfo(required = true, sinceVersion = 1, bytesPosition = 6)
    override val timestamp: Timestamp,
    @FieldInfo(required = false, sinceVersion = 2, bytesPosition = 7)
    val feeAssetId: FeeAssetId? = null,
    @FieldInfo(required = false, sinceVersion = 3, bytesPosition = 8)
    override val atomicBadge: AtomicBadge? = null,
    @FieldInfo(required = true, sinceVersion = 4, bytesPosition = 9)
    val validationPolicy: ValidationPolicy? = null,
    @FieldInfo(required = true, sinceVersion = 4, bytesPosition = 10)
    val apiVersion: ContractApiVersion? = null,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx, ExecutableTx, AtomicInnerTx {
    override fun withId(id: TxId): Tx = copy(id = id)

    override fun withProof(proof: Signature): Tx = copy(proofs = proofs?.plus(proof) ?: listOf(proof))

    override fun withSenderAddress(senderAddress: Address): Tx = copy(senderAddress = senderAddress)
}
