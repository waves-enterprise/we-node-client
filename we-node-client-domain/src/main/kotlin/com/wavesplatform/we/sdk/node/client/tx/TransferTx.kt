package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.AtomicBadge
import com.wavesplatform.we.sdk.node.client.Attachment
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.atomic.HasAtomicBadge

data class TransferTx(
    override val id: TxId,
    val senderPublicKey: PublicKey,
    val assetId: AssetId? = null,
    val feeAssetId: FeeAssetId? = null,
    override val timestamp: Timestamp,
    val amount: Amount,
    val fee: Fee,
    val recipient: Address,
    val attachment: Attachment? = null,
    override val atomicBadge: AtomicBadge? = null,
    val proofs: List<Proof>? = null,
    val senderAddress: Address,
    val version: TxVersion,
) : Tx, AtomicInnerTx, HasAtomicBadge<TransferTx> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): TransferTx =
        copy(atomicBadge = atomicBadge)
}
