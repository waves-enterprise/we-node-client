package com.wavesplatform.we.sdk.node.client.sign

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.NodeName
import com.wavesplatform.we.sdk.node.client.OpType
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.RegisterNodeTx

data class RegisterNodeSignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val opType: OpType,
    val target: Address,
    val targetPublicKey: PublicKey,
    val nodeName: NodeName,
) : SignRequest<RegisterNodeTx>
