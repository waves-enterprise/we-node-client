package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Attachment
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge

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
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx, AtomicInnerTx
