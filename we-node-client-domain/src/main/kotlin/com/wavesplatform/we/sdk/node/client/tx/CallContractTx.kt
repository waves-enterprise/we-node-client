package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AtomicBadge
import com.wavesplatform.we.sdk.node.client.ContractId
import com.wavesplatform.we.sdk.node.client.ContractVersion
import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion

data class CallContractTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val contractId: ContractId,
    val params: List<DataEntry>,
    val fee: Fee,
    override val timestamp: Timestamp,
    val contractVersion: ContractVersion,
    val feeAssetId: FeeAssetId? = null,
    val atomicBadge: AtomicBadge? = null,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
    val version: TxVersion,
) : Tx, ExecutableTx, AtomicInnerTx
