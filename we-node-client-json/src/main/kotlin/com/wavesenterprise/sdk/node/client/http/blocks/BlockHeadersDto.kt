package com.wavesenterprise.sdk.node.client.http.blocks

import com.fasterxml.jackson.annotation.JsonProperty
import com.wavesenterprise.sdk.node.client.http.blocks.PoaConsensusDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.blocks.PosConsensusDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.BlockVersion
import com.wavesenterprise.sdk.node.domain.Feature
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.blocks.BlockHeaders

data class BlockHeadersDto(
    val reference: String,
    @JsonProperty("blocksize")
    val blockSize: Long,
    val features: List<Int> = emptyList(),
    val signature: String,
    val generator: String,
    val transactionCount: Long,
    val version: Int,
    @JsonProperty("poa-consensus")
    var poaConsensus: PoaConsensusDto? = null,
    @JsonProperty("pos-consensus")
    var posConsensus: PosConsensusDto? = null,
    val timestamp: Long,
    val height: Long,
) {
    companion object {
        @JvmStatic
        fun BlockHeadersDto.toDomain() = BlockHeaders(
            reference = reference,
            blockSize = blockSize,
            features = features.map { Feature(it) },
            signature = Signature.fromBase58(signature),
            generator = generator,
            transactionCount = transactionCount,
            version = BlockVersion(version),
            poaConsensus = poaConsensus?.toDomain(),
            posConsensus = posConsensus?.toDomain(),
            timestamp = timestamp,
            height = Height(height),
        )
    }
}
