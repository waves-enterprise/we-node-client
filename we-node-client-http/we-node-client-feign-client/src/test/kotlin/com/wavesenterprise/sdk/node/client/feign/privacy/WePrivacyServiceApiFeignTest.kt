package com.wavesenterprise.sdk.node.client.feign.privacy

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorDecoder
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorMapper
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@WireMockTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WePrivacyServiceApiFeignTest {

    lateinit var wePrivacyServiceApiFeign: WePrivacyServiceApiFeign

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        wePrivacyServiceApiFeign = FeignWeApiFactory.createClient(
            clientClass = WePrivacyServiceApiFeign::class.java,
            feignProperties = FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl),
            errorDecoder = FeignNodeErrorDecoder(FeignNodeErrorMapper(jacksonObjectMapper())),
        )
    }

    @Test
    fun `should return hashes by policy id`() {
        val expectedHashes = listOf(
            "DP5MggKC8GJuLZshCVNSYwBtE6WTRtMM1YPPdcmwbuNg",
            "C2HM9q3QzGSBydnCA4GMcf3cFnTaSuwaWXVtsCSTSmZW"
        )
        wePrivacyServiceApiFeign.getPolicyHashes("5inUANAmDzRfq5f1Yv7HBTm8G4AREPfeKCntaEDDqVbU").apply {
            assertEquals(expectedHashes, this)
        }
    }

    @Test
    fun `should return owners by policy id`() {
        val expectedHashes = listOf(
            "DP5MggKC8GJuLZshCVNSYwBtE6WTRtMM1YPPdcmwbuNg",
            "C2HM9q3QzGSBydnCA4GMcf3cFnTaSuwaWXVtsCSTSmZW"
        )
        wePrivacyServiceApiFeign.getPolicyOwners("5inUANAmDzRfq5f1Yv7HBTm8G4AREPfeKCntaEDDqVbU").apply {
            assertEquals(expectedHashes, this)
        }
    }

    @Test
    fun `should return recipients by policy id`() {
        val expectedHashes = listOf(
            "DP5MggKC8GJuLZshCVNSYwBtE6WTRtMM1YPPdcmwbuNg",
            "C2HM9q3QzGSBydnCA4GMcf3cFnTaSuwaWXVtsCSTSmZW"
        )
        wePrivacyServiceApiFeign.getPolicyRecipients("5inUANAmDzRfq5f1Yv7HBTm8G4AREPfeKCntaEDDqVbU").apply {
            assertEquals(expectedHashes, this)
        }
    }

    @Test
    @Disabled
    fun `should return data from privacy`() {
        val expectedByteArrayData = "bytes".toByteArray()
        wePrivacyServiceApiFeign.getDataFromPrivacy(
            policyId = "5inUANAmDzRfq5f1Yv7HBTm8G4AREPfeKCntaEDDqVbU",
            policyItemHash = "5inUANAmDzRfq5f1Yv7HBTm8G4AREPfeKCntaEDDqVbU",
        ).apply {
            assertEquals(expectedByteArrayData, this)
        }
    }
}
