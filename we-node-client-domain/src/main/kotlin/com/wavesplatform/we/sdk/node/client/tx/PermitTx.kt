package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AtomicBadge
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PermissionOp
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.atomic.HasAtomicBadge

data class PermitTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val target: Address,
    override val timestamp: Timestamp,
    val fee: Fee,
    val permissionOp: PermissionOp,
    override val atomicBadge: AtomicBadge? = null,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
    val version: TxVersion,
) : Tx, AtomicInnerTx, HasAtomicBadge<PermitTx> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): PermitTx =
        copy(atomicBadge = atomicBadge)
}
