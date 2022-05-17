package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion

data class GenesisTx(
    override val id: TxId,
    val recipient: Address,
    val amount: Amount,
    val fee: Fee,
    override val timestamp: Timestamp,
    val signature: Signature,
    override val version: TxVersion,
) : Tx
