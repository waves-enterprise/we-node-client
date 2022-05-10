package com.wavesplatform.we.sdk.node.client.sign

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AtomicBadge
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.OpType
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.PermitTx

data class PermitSignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val target: Address,
    val opType: OpType,
    val dueTimestamp: Timestamp,
    val atomicBadge: AtomicBadge? = null,
) : SignRequest<PermitTx>
