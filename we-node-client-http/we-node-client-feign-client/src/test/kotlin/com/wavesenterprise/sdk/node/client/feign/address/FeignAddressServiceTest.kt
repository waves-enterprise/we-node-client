package com.wavesenterprise.sdk.node.client.feign.address

import com.wavesenterprise.sdk.node.client.http.DataEntryDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.address.AddressDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.address.SignMessageResponseDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.address.VerifyMessageSignatureResponseDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.specific.DataKeyNotExistsException
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.address
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.dataEntry
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.dataKey
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.publicKey
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.signMessageRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.signMessageResponse
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.verifyMessageSignatureRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.verifyMessageSignatureResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.Optional

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeignAddressServiceTest {
    private lateinit var feignAddressService: FeignAddressService
    private val weAddressServiceApiFeign: WeAddressServiceApiFeign = mockk()

    @BeforeEach
    fun setUp() {
        feignAddressService = FeignAddressService(
            weAddressServiceApiFeign = weAddressServiceApiFeign,
        )
    }

    @Test
    fun `should return addresses`() {
        val expectedResponse = listOf(address(), address())
        every {
            weAddressServiceApiFeign.getAddresses()
        } returns expectedResponse.map { it.asBase58String() }
        assertEquals(
            expectedResponse,
            feignAddressService.getAddresses()
        )
    }

    @Test
    fun `should return address by publicKey`() {
        val expectedResponse = address()
        every {
            weAddressServiceApiFeign.getAddressByPublicKey(any())
        } returns expectedResponse.toDto()
        assertEquals(
            expectedResponse,
            feignAddressService.getAddressByPublicKey(
                publicKey = publicKey(),
            )
        )
    }

    @Test
    fun `should return address value`() {
        val expectedResponse = Optional.of(dataEntry())
        every {
            weAddressServiceApiFeign.getAddressValue(any(), any())
        } returns expectedResponse.map { it.toDto() }
        assertEquals(
            expectedResponse,
            feignAddressService.getAddressValue(
                address = address(),
                key = dataKey(),
            )
        )
    }

    @Test
    fun `should return address values`() {
        val expectedResponse = listOf(dataEntry(), dataEntry())
        every {
            weAddressServiceApiFeign.getAddressValues(any())
        } returns expectedResponse.map { it.toDto() }
        assertEquals(
            expectedResponse,
            feignAddressService.getAddressValues(
                address = address(),
            )
        )
    }

    @Test
    fun `should return sign message response`() {
        val expectedResponse = signMessageResponse()
        every {
            weAddressServiceApiFeign.signMessage(any(), any())
        } returns expectedResponse.toDto()
        assertEquals(
            expectedResponse,
            feignAddressService.signMessage(
                address = address(),
                request = signMessageRequest()
            )
        )
    }

    @Test
    fun `should return verify message signature response`() {
        val expectedResponse = verifyMessageSignatureResponse()
        every {
            weAddressServiceApiFeign.verifyMessageSignature(any(), any())
        } returns expectedResponse.toDto()
        assertEquals(
            expectedResponse,
            feignAddressService.verifyMessageSignature(
                address = address(),
                request = verifyMessageSignatureRequest()
            )
        )
    }

    @Test
    fun `should return null when catch DataKeyNotExistsException`() {
        feignAddressService = FeignAddressService(weAddressServiceApiFeign)
        every {
            weAddressServiceApiFeign.getAddressValue(any(), any())
        } throws DataKeyNotExistsException(
            nodeError = NodeError(error = 304, message = "no data for this key"),
            cause = Exception(),
        )
        assertEquals(
            Optional.empty<DataEntry>(),
            feignAddressService.getAddressValue(
                address = Address.fromBase58("3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX"),
                key = DataKey("not-existing-key")
            )
        )
    }
}
