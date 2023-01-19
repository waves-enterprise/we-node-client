package com.wavesenterprise.sdk.node.domain.http.blocks

import com.fasterxml.jackson.annotation.JsonProperty
import com.wavesenterprise.sdk.node.domain.blocks.PoaConsensus

data class PoaConsensusDto(
    @JsonProperty("overall-skipped-rounds")
    val overallSkippedRound: Long,
) {
    companion object {
        @JvmStatic
        fun PoaConsensusDto.toDomain() = PoaConsensus(
            overallSkippedRound = overallSkippedRound,
        )
    }
}
