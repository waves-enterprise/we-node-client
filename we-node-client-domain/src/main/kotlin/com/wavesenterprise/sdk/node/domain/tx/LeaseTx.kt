package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion

data class LeaseTx(
    override val id: TxId,
    val assetId: AssetId? = null,
    val senderPublicKey: PublicKey,
    val recipient: Address,
    val amount: Amount,
    val fee: Fee,
    override val timestamp: Timestamp,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx
