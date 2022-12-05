package com.wavesenterprise.sdk.node.client.feign

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.node.WeAddressServiceApiFeign
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
            WeAddressServiceApiFeign::class.java,
            FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl)
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
