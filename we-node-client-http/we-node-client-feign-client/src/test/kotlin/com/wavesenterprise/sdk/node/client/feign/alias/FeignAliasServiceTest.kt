package com.wavesenterprise.sdk.node.client.feign.alias

import com.wavesenterprise.sdk.node.client.http.AddressDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Alias
import com.wavesenterprise.sdk.node.domain.Alias.Companion.toDomain
import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.specific.AliasNotExistException
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.address
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.Optional

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeignAliasServiceTest {
    private lateinit var feignAddressService: FeignAliasService
    private val weAliasServiceApiFeign: WeAliasServiceApiFeign = mockk()

    @BeforeEach
    fun setUp() {
        feignAddressService = FeignAliasService(
            weAliasServiceApiFeign = weAliasServiceApiFeign,
        )
    }

    @Test
    fun `should return aliases by address`() {
        val address = address()
        val expectedResponse = listOf("alias:R:_firstAlias", "alias:R:_secondAlias")
        every {
            weAliasServiceApiFeign.getAliasesByAddress(any())
        } returns expectedResponse
        assertEquals(
            expectedResponse.map { it.toDomain() },
            feignAddressService.getAliasesByAddress(address),
        )
    }

    @Test
    fun `should return address by alias`() {
        val expectedResponse = Optional.of(address())
        every {
            weAliasServiceApiFeign.getAddressByAlias(any())
        } returns expectedResponse.map { it.toDto() }
        assertEquals(
            expectedResponse,
            feignAddressService.getAddressByAlias(Alias("_alias1")),
        )
    }

    @Test
    fun `should return null when catch AliasNotExistException`() {
        every {
            weAliasServiceApiFeign.getAddressByAlias(any())
        } throws AliasNotExistException(
            nodeError = NodeError(error = 302, message = "alias 'alias:R:_non-existent-alias' doesn't exist"),
            cause = Exception(),
        )
        assertEquals(
            Optional.empty<Address>(),
            feignAddressService.getAddressByAlias(Alias("_non-existent-alias")),
        )
    }
}
