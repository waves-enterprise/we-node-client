package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Role
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion

data class GenesisPermitTx(
    override val id: TxId,
    val target: Address,
    val role: Role,
    val fee: Fee,
    override val timestamp: Timestamp,
    val signature: Signature,
    override val version: TxVersion,
) : Tx
