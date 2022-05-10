package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion

data class LeaseTx(
    override val id: TxId,
    val assetId: AssetId? = null,
    val senderPublicKey: PublicKey,
    val recipient: Address,
    val amount: Amount,
    val fee: Fee,
    override val timestamp: Timestamp,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
    val version: TxVersion,
) : Tx
