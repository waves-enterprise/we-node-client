package com.wavesenterprise.sdk.node.domain.http.blocks

import com.fasterxml.jackson.annotation.JsonProperty
import com.wavesenterprise.sdk.node.domain.blocks.PosConsensus

data class PosConsensusDto(
    @JsonProperty("base-target")
    val baseTarget: Long,
    @JsonProperty("generation-signature")
    val generationSignature: String,
) {
    companion object {
        @JvmStatic
        fun PosConsensusDto.toDomain() = PosConsensus(
            baseTarget = baseTarget,
            generationSignature = generationSignature,
        )
    }
}
