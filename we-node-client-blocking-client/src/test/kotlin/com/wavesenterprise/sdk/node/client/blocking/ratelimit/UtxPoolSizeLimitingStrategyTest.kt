package com.wavesenterprise.sdk.node.client.blocking.ratelimit

import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.client.blocking.txCount
import com.wavesenterprise.sdk.node.client.blocking.utxSize
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UtxPoolSizeLimitingStrategyTest {

    private val txService: TxService = mockk()

    lateinit var utxPoolSizeLimitingStrategy: UtxPoolSizeLimitingStrategy

    @BeforeEach
    fun setUp() {
        utxPoolSizeLimitingStrategy = UtxPoolSizeLimitingStrategy(
            txService = txService,
            maxUtx = MAX_UTX,
        )
    }

    @Test
    fun `should return false when utxSize less than maxUtx`() {
        every {
            txService.utxSize()
        } returns utxSize(txCount = txCount(MAX_UTX - 1))

        assertFalse(utxPoolSizeLimitingStrategy.isLimitExceeded())
    }

    @Test
    fun `should return true when utxSize more than maxUtx`() {
        every {
            txService.utxSize()
        } returns utxSize(txCount = txCount(MAX_UTX + 1))

        assertTrue(utxPoolSizeLimitingStrategy.isLimitExceeded())
    }

    companion object {
        const val MAX_UTX = 10
    }
}
