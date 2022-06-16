package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.NodeName
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion

data class RegisterNodeTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val target: Address,
    val targetPublicKey: PublicKey,
    val nodeName: NodeName,
    val opType: OpType,
    override val timestamp: Timestamp,
    val fee: Fee,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx
