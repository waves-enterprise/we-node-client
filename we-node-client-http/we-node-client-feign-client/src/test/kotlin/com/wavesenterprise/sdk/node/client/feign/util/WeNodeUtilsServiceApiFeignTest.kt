package com.wavesenterprise.sdk.node.client.feign.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorDecoder
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorMapper
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@WireMockTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeNodeUtilsServiceApiFeignTest {

    lateinit var weUtilsServiceApiFeign: WeUtilsServiceApiFeign

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        weUtilsServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeUtilsServiceApiFeign::class.java,
            feignProperties = FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl),
            errorDecoder = FeignNodeErrorDecoder(FeignNodeErrorMapper(jacksonObjectMapper())),
        )
    }

    @Test
    fun `should get current time`() {
        weUtilsServiceApiFeign.currentTime().apply {
            assertEquals(1687343013465, system)
            assertEquals(1687343013465, ntp)
        }
    }

    @Test
    fun `should get hashed message by hashSecure`() {
        weUtilsServiceApiFeign.hashSecure("test").apply {
            assertEquals("test", message)
            assertEquals("2FnJ3K7yJaei4jjEMB6b22MjUmBtneSjpE5xqWRuAG6r", hash)
        }
    }

    @Test
    fun `should get hashed message by fastHash`() {
        weUtilsServiceApiFeign.fastHash("test").apply {
            assertEquals("test", message)
            assertEquals("2FnJ3K7yJaei4jjEMB6b22MjUmBtneSjpE5xqWRuAG6r", hash)
        }
    }
}
