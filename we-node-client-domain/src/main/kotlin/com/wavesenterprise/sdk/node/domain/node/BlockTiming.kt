package com.wavesenterprise.sdk.node.domain.node

import java.time.Duration

sealed interface BlockTiming {
    data class PoaRoundInfo(
        val roundDuration: Duration,
        val syncDuration: Duration,
    ) : BlockTiming

    data class PosRoundInfo(
        val averageBlockDelay: Duration,
    ) : BlockTiming
}
