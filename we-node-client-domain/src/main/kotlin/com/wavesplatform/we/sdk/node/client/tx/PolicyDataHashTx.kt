package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AtomicBadge
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId

data class PolicyDataHashTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val dataHash: Hash,
    val policyId: PolicyId,
    override val timestamp: Timestamp,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val atomicBadge: AtomicBadge? = null,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
) : Tx, AtomicInnerTx
