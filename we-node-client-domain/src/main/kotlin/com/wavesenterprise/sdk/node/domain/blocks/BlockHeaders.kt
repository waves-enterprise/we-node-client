package com.wavesenterprise.sdk.node.domain.blocks

import com.wavesenterprise.sdk.node.domain.BlockVersion
import com.wavesenterprise.sdk.node.domain.Feature
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.Signature

data class BlockHeaders(
    val reference: String,
    val blockSize: Long,
    val features: List<Feature> = emptyList(),
    val signature: Signature,
    val generator: String,
    val transactionCount: Long,
    val version: BlockVersion,
    var poaConsensus: PoaConsensus? = null,
    var posConsensus: PosConsensus? = null,
    val timestamp: Long,
    val height: Height,
)
