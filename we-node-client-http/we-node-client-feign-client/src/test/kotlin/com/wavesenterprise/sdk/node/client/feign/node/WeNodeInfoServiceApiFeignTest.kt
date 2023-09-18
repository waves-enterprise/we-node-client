package com.wavesenterprise.sdk.node.client.feign.node

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
class WeNodeInfoServiceApiFeignTest {

    lateinit var weNodeInfoServiceApiFeign: WeNodeInfoServiceApiFeign

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        weNodeInfoServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeNodeInfoServiceApiFeign::class.java,
            feignProperties = FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl),
            errorDecoder = FeignNodeErrorDecoder(FeignNodeErrorMapper(jacksonObjectMapper())),
        )
    }

    @Test
    fun `should get node owner`() {
        val nodeOwnerAddressDto = weNodeInfoServiceApiFeign.getNodeOwnerAddress()

        nodeOwnerAddressDto.apply {
            assertEquals("3LGBihYhwjDhHbTZKK7eP5oTMHYe5tA14Yf", address)
            assertEquals("FcWEYLLxVJ1ngfvhYv17ycjhWczopW32nt3wvnNqweSc", publicKey)
        }
    }
}
