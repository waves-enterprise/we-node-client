package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion

data class GenesisRegisterNodeTx(
    override val id: TxId,
    val targetPublicKey: PublicKey,
    val fee: Fee,
    override val timestamp: Timestamp,
    val signature: Signature,
    override val version: TxVersion,
) : Tx
