package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion

data class AtomicTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val miner: Address? = null,
    val txs: List<AtomicInnerTx>,
    override val timestamp: Timestamp,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
    val fee: Fee,
    val version: TxVersion,
) : Tx
