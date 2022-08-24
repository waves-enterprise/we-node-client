package com.wavesenterprise.sdk.node.client.feign

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.contract.WeContractServiceApiFeign
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@WireMockTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WeContractServiceApiFeignTest {

    lateinit var weContractServiceApiFeign: WeContractServiceApiFeign

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        weContractServiceApiFeign = FeignWeApiFactory.createClient(
            WeContractServiceApiFeign::class.java,
            FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl)
        )
    }

    @Test
    fun `should get contract key`() {
        val dataEntryDto = weContractServiceApiFeign.contractKey(
            contractId = "8mYpzj5rVVQSp2DgsyMvoMSViHtX94dJHkPyX2xo855y",
            key = "KEY",
        ).get()

        dataEntryDto.apply {
            assertEquals("KEY", key)
            assertEquals("string", type)
            assertEquals("value", value)
        }
    }

    @Test
    fun `should return null for getting contract key`() {
        val dataEntryDto = weContractServiceApiFeign.contractKey(
            contractId = "5inUANAmDzRfq5f1Yv7HBTm8G4AREPfeKCntaEDDqVbU",
            key = "KEY",
        )
        assertFalse(dataEntryDto.isPresent)
    }

    @Test
    fun `should get contract keys`() {
        val dataEntryDtoList = weContractServiceApiFeign.contractKeys(
            contractId = "8mYpzj5rVVQSp2DgsyMvoMSViHtX94dJHkPyX2xo855y",
        )

        dataEntryDtoList.apply {
            get(0).apply {
                assertEquals("KEY_BOOLEAN", key)
                assertEquals("boolean", type)
                assertEquals(true, value)
            }
            get(1).apply {
                assertEquals("KEY_INT", key)
                assertEquals("integer", type)
                assertEquals(1, value)
            }
            get(2).apply {
                assertEquals("KEY_STRING", key)
                assertEquals("string", type)
                assertEquals("value", value)
            }
        }
    }
}
