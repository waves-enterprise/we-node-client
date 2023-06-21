package com.wavesenterprise.sdk.node.client.feign.util

import com.wavesenterprise.sdk.node.client.http.util.HashedMessageDto
import com.wavesenterprise.sdk.node.client.http.util.TimeDto
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeignUtilsServiceTest {

    private val weUtilsServiceApiFeign: WeUtilsServiceApiFeign = mockk()

    private lateinit var feignUtilsService: FeignUtilsService

    @BeforeEach
    fun init() {
        feignUtilsService = FeignUtilsService(weUtilsServiceApiFeign)
    }

    @Test
    fun `should correct map TimeDto to domain`() {
        val timeDto = TimeDto(
            system = 0,
            ntp = 0,
        )
        every { weUtilsServiceApiFeign.currentTime() } returns timeDto
        feignUtilsService.currentTime().apply {
            assertEquals(timeDto.system, system)
            assertEquals(timeDto.ntp, ntp)
        }
    }

    @Test
    fun `should correct map HashedMessageDto to domain by hashSecure`() {
        val message = "test"
        val hashedMessageDto = HashedMessageDto(
            message = message,
            hash = "2FnJ3K7yJaei4jjEMB6b22MjUmBtneSjpE5xqWRuAG6r",
        )
        every { weUtilsServiceApiFeign.hashSecure(message) } returns hashedMessageDto
        feignUtilsService.hashSecure(message).apply {
            assertEquals(hashedMessageDto.message, message)
            assertEquals(hashedMessageDto.hash, hash.asBase58String())
        }
    }

    @Test
    fun `should correct map HashedMessageDto to domain by fastHash`() {
        val message = "test"
        val hashedMessageDto = HashedMessageDto(
            message = message,
            hash = "2FnJ3K7yJaei4jjEMB6b22MjUmBtneSjpE5xqWRuAG6r",
        )
        every { weUtilsServiceApiFeign.fastHash(message) } returns hashedMessageDto
        feignUtilsService.fastHash(message).apply {
            assertEquals(hashedMessageDto.message, message)
            assertEquals(hashedMessageDto.hash, hash.asBase58String())
        }
    }
}
