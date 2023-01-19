package com.wavesenterprise.sdk.node.domain.blocks

import com.wavesenterprise.sdk.node.domain.BlockVersion
import com.wavesenterprise.sdk.node.domain.Feature
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.tx.Tx

data class BlockAtHeight(
    val reference: String,
    val blockSize: Long,
    val features: List<Feature> = emptyList(),
    val signature: Signature,
    val fee: Fee,
    val generator: String,
    val transactionCount: Long,
    val transactions: List<Tx>,
    val version: BlockVersion,
    var poaConsensus: PoaConsensus? = null,
    var posConsensus: PosConsensus? = null,
    val timestamp: Long,
    val height: Height,
)
