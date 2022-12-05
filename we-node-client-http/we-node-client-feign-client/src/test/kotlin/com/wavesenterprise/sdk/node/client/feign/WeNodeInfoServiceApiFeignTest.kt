package com.wavesenterprise.sdk.node.client.feign

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.node.WeNodeInfoServiceApiFeign
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
            WeNodeInfoServiceApiFeign::class.java,
            FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl)
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
