package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion

data class CallContractTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val contractId: ContractId,
    val params: List<DataEntry>,
    val fee: Fee,
    override val timestamp: Timestamp,
    val contractVersion: ContractVersion,
    val feeAssetId: FeeAssetId? = null,
    override val atomicBadge: AtomicBadge? = null,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx, ExecutableTx, AtomicInnerTx, AtomicSignInnerTx<CallContractTx> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): CallContractTx =
        copy(atomicBadge = atomicBadge)
}
