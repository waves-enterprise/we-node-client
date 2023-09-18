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
class WeAddressServiceApiFeignTest {

    lateinit var weAddressServiceApiFeign: WeAddressServiceApiFeign

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        weAddressServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WeAddressServiceApiFeign::class.java,
            feignProperties = FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl),
            errorDecoder = FeignNodeErrorDecoder(FeignNodeErrorMapper(jacksonObjectMapper())),
        )
    }

    @Test
    fun `should get addresses`() {
        val addressesList = weAddressServiceApiFeign.getAddresses()

        assertEquals(
            listOf(
                "3M3xGmJGmxBv2aZ4UFmn93rHxVXTJDKSAnh",
                "3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv",
                "3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX",
            ),
            addressesList,
        )
    }
}
