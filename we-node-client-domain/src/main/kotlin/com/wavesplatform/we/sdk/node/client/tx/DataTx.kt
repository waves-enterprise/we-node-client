package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.PermitDataEntry
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion

data class DataTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val authorPublicKey: PublicKey,
    val data: List<PermitDataEntry>,
    override val timestamp: Timestamp,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    val authorAddress: Address,
    override val version: TxVersion,
) : Tx
