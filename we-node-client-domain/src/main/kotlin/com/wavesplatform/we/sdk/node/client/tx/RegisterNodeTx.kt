package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.NodeName
import com.wavesplatform.we.sdk.node.client.OpType
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion

data class RegisterNodeTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val target: Address,
    val targetPublicKey: PublicKey,
    val nodeName: NodeName,
    val opType: OpType,
    override val timestamp: Timestamp,
    val fee: Fee,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
    val version: TxVersion,
) : Tx
