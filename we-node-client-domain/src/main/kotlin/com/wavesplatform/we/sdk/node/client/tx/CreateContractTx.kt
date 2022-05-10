package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AtomicBadge
import com.wavesplatform.we.sdk.node.client.ContractApiVersion
import com.wavesplatform.we.sdk.node.client.ContractImage
import com.wavesplatform.we.sdk.node.client.ContractName
import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.ValidationPolicy
import com.wavesplatform.we.sdk.node.client.atomic.HasAtomicBadge

data class CreateContractTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val image: ContractImage,
    val imageHash: Hash,
    val contractName: ContractName,
    val params: List<DataEntry>,
    val fee: Fee,
    override val timestamp: Timestamp,
    val feeAssetId: FeeAssetId? = null,
    override val atomicBadge: AtomicBadge? = null,
    val validationPolicy: ValidationPolicy? = null,
    val apiVersion: ContractApiVersion? = null,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
    val version: TxVersion,
) : Tx, ExecutableTx, AtomicInnerTx, HasAtomicBadge<CreateContractTx> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): CreateContractTx =
        copy(atomicBadge = atomicBadge)
}
