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

data class UpdateContractTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val contractId: ContractId,
    val image: ContractImage,
    val imageHash: ContractImageHash,
    val fee: Fee,
    override val timestamp: Timestamp,
    val feeAssetId: FeeAssetId? = null,
    override val atomicBadge: AtomicBadge? = null,
    val validationPolicy: ValidationPolicy? = null,
    val apiVersion: ContractApiVersion? = null,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx, ExecutableTx, AtomicInnerTx, AtomicSignInnerTx<UpdateContractTx> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): UpdateContractTx =
        copy(atomicBadge = atomicBadge)
}
