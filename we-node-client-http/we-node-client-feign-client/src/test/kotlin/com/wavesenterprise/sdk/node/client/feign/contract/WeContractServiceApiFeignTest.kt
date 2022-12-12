package com.wavesenterprise.sdk.node.client.feign.contract

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorDecoder
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorMapper
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import com.wavesenterprise.sdk.node.exception.specific.ContractNotFoundException
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@WireMockTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WeContractServiceApiFeignTest {

    lateinit var weContractServiceApiFeign: WeContractServiceApiFeign

    private val feignNodeErrorDecoder: FeignNodeErrorDecoder = spyk(
        FeignNodeErrorDecoder(
            FeignNodeErrorMapper(
                jacksonObjectMapper()
            )
        )
    )

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        weContractServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeContractServiceApiFeign::class.java,
            feignProperties = FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl),
            errorDecoder = feignNodeErrorDecoder,
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

    @Test
    fun `should call FeignNodeErrorMapper`() {
        assertThrows<ContractNotFoundException> {
            weContractServiceApiFeign.contractKey(
                contractId = "C2HM9q3QzGSBydnCA4GMcf3cFnTaSuwaWXVtsCSTSmZW",
                key = "KEY",
            )
        }
    }
}
