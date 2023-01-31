package com.wavesenterprise.sdk.node.client.blocking.ratelimit

import com.wavesenterprise.sdk.node.client.blocking.tx.TxService

class UtxPoolSizeLimitingStrategy(
    private val txService: TxService,
    private val maxUtx: Int,
) : RateLimitingStrategy {
    override fun isLimitExceeded() = txService.utxInfo().txCount.value >= maxUtx
}
