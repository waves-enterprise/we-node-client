package com.wavesenterprise.sdk.node.client.feign.pki

import com.wavesenterprise.sdk.node.client.http.pki.PkiVerifyResponseDto
import com.wavesenterprise.sdk.node.domain.pki.PkiVerifyRequest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FeignPkiServiceTest {

    private val wePkiServiceApiFeign: WePkiServiceApiFeign = mockk()
    private lateinit var feignPkiService: FeignPkiService

    @BeforeEach
    fun init() {
        feignPkiService = FeignPkiService(wePkiServiceApiFeign)
    }

    @Test
    fun `should return correct signature status`() {
        val expectedSigStatus = true
        every { wePkiServiceApiFeign.verify(any()) } returns PkiVerifyResponseDto(sigStatus = expectedSigStatus)

        feignPkiService.verify(
            PkiVerifyRequest(
                inputData = "data",
                signature = "signature",
                sigType = 1,
                extendedKeyUsageList = emptyList(),
            ),
        ).also {
            assertEquals(expectedSigStatus, it.sigStatus)
        }
    }
}
