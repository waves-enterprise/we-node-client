package com.wavesenterprise.sdk.node.client.feign.tx

import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.exception.NodeNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeignTxServiceTest {

    private val weTxApiFeign: WeTxApiFeign = mockk()

    private lateinit var feignTxService: FeignTxService

    @BeforeEach
    fun init() {
        feignTxService = FeignTxService(weTxApiFeign)
    }

    @Test
    fun `should return null when catch NodeNotFoundException`() {
        val txId = TxId.fromBase58("CgqRPcPnexY533gCh2SSvBXh5bca1qMs7KFGntawHGww")
        every { weTxApiFeign.txInfo(txId.asBase58String()) } throws NodeNotFoundException(cause = Exception())
        val txInfo = feignTxService.txInfo(txId)
        assertFalse(txInfo.isPresent)
    }
}
