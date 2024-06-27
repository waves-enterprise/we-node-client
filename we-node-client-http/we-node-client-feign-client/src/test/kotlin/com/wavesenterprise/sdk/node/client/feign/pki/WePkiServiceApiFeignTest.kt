package com.wavesenterprise.sdk.node.client.feign.pki

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorDecoder
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorMapper
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import com.wavesenterprise.sdk.node.client.http.pki.PkiVerifyRequestDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@WireMockTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WePkiServiceApiFeignTest {
    lateinit var wePkiServiceApiFeign: WePkiServiceApiFeign

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        wePkiServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WePkiServiceApiFeign::class.java,
            feignProperties = FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl),
            errorDecoder = FeignNodeErrorDecoder(FeignNodeErrorMapper(jacksonObjectMapper())),
        )
    }

    @Test
    fun `should return signature status`() {
        wePkiServiceApiFeign.verify(
            PkiVerifyRequestDto(
                inputData = "data",
                signature = "signature",
                sigType = 1,
                extendedKeyUsageList = emptyList(),
            ),
        ).also {
            assertEquals(true, it.sigStatus)
        }
    }
}
