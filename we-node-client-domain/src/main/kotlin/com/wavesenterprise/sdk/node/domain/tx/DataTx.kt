package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PermitDataEntry
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion

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
