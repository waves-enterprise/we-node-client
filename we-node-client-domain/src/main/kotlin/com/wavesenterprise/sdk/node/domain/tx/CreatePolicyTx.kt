package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PolicyDescription
import com.wavesenterprise.sdk.node.domain.PolicyName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge

data class CreatePolicyTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val policyName: PolicyName,
    val description: PolicyDescription,
    val recipients: List<Address>,
    val owners: List<Address>,
    override val timestamp: Timestamp,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    override val atomicBadge: AtomicBadge? = null,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx, AtomicInnerTx, AtomicSignInnerTx<CreatePolicyTx> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): CreatePolicyTx =
        copy(atomicBadge = atomicBadge)
}
