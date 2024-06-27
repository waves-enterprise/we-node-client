package com.wavesenterprise.sdk.node.client.http.node

import com.wavesenterprise.sdk.node.domain.node.BlockTiming
import java.time.Duration

sealed interface BlockTimingDto {
    data class PoaRoundInfoDto(
        val roundDuration: Duration,
        val syncDuration: Duration,
    ) : BlockTimingDto

    data class PosRoundInfoDto(
        val averageBlockDelay: Duration,
    ) : BlockTimingDto

    companion object {
        @JvmStatic
        fun BlockTimingDto.toDomain() =
            when (this) {
                is PoaRoundInfoDto -> {
                    BlockTiming.PoaRoundInfo(
                        roundDuration = roundDuration,
                        syncDuration = syncDuration,
                    )
                }
                is PosRoundInfoDto -> {
                    BlockTiming.PosRoundInfo(
                        averageBlockDelay = averageBlockDelay,
                    )
                }
            }
    }
}
