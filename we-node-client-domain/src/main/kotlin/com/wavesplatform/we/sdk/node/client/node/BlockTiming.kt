package com.wavesplatform.we.sdk.node.client.node

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
