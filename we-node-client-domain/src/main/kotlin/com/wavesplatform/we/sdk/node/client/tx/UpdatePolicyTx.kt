package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.OpType
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.atomic.AtomicBadge

data class UpdatePolicyTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val policyId: PolicyId,
    val recipients: List<Address>,
    val owners: List<Address>,
    val opType: OpType,
    override val timestamp: Timestamp,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    override val atomicBadge: AtomicBadge? = null,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
    val version: TxVersion,
) : Tx, AtomicInnerTx, AtomicSignInnerTx<UpdatePolicyTx> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): UpdatePolicyTx =
        copy(atomicBadge = atomicBadge)
}
