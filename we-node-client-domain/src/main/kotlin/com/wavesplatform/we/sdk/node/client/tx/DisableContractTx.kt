package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.ContractId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.atomic.AtomicBadge

data class DisableContractTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val contractId: ContractId,
    val fee: Fee,
    override val timestamp: Timestamp,
    val feeAssetId: FeeAssetId? = null,
    override val atomicBadge: AtomicBadge? = null,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
    val version: TxVersion,
) : Tx, AtomicInnerTx, AtomicSignInnerTx<DisableContractTx> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): DisableContractTx =
        copy(atomicBadge = atomicBadge)
}
