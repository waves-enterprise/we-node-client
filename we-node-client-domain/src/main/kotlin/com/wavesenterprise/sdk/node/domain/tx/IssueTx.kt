package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Decimals
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.IssueTxDescription
import com.wavesenterprise.sdk.node.domain.IssueTxName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Quantity
import com.wavesenterprise.sdk.node.domain.Script
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion

data class IssueTx(
    override val id: TxId,
    val chainId: ChainId,
    val senderPublicKey: PublicKey,
    val name: IssueTxName,
    val description: IssueTxDescription,
    val quantity: Quantity,
    val decimals: Decimals,
    val reissuable: Boolean,
    val fee: Fee,
    override val timestamp: Timestamp,
    val script: Script? = null,
    val proofs: List<Signature>? = null,
    val senderAddress: Address,
    override val version: TxVersion,
) : Tx
