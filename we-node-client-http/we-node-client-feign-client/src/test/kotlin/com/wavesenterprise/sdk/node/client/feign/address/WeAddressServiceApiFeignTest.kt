package com.wavesenterprise.sdk.node.client.feign.address

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorDecoder
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorMapper
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import com.wavesenterprise.sdk.node.client.http.DataEntryDto
import com.wavesenterprise.sdk.node.client.http.address.AddressDto
import com.wavesenterprise.sdk.node.client.http.address.SignMessageRequestDto
import com.wavesenterprise.sdk.node.client.http.address.SignMessageResponseDto
import com.wavesenterprise.sdk.node.client.http.address.VerifyMessageSignatureRequestDto
import com.wavesenterprise.sdk.node.client.http.address.VerifyMessageSignatureResponseDto
import com.wavesenterprise.sdk.node.exception.NodeErrorCode
import com.wavesenterprise.sdk.node.exception.specific.InvalidPasswordException
import com.wavesenterprise.sdk.node.exception.specific.InvalidPublicKeyException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.util.Optional

@WireMockTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeAddressServiceApiFeignTest {

    lateinit var weAddressServiceApiFeign: WeAddressServiceApiFeign

    private val address = "3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX"
    private val publicKey = "4L4XEpNpesX9r6rVJ8hW1TrMiNCZ6SMvRuWPKB7T47wKfnp4D84XBUv7xsa36CGwoyK3fzfojivwonHNrsX2fLBL"
    private val signature = "2CE7zZqJ2zXphCweDbvMMCvYYcc7AsHHNdf9jGLHDX4FMqN1G7HwN2SF91rdUyBP6oKRYqNE1gcVU2xJuM1B4mEX"

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
        assertEquals(
            listOf(
                "3M3xGmJGmxBv2aZ4UFmn93rHxVXTJDKSAnh",
                "3M7EEnszPAT2yr72SgWVDLxfYCa4AYvVRwv",
                "3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX",
            ),
            weAddressServiceApiFeign.getAddresses(),
        )
    }

    @Test
    fun `should get address by publicKey`() {
        assertEquals(
            AddressDto(address),
            weAddressServiceApiFeign.getAddressByPublicKey(publicKey)
        )
    }

    @Test
    fun `should get address value`() {
        val addressValue = DataEntryDto(
            type = "integer",
            key = "some-key",
            value = 100,
        )
        assertEquals(
            Optional.of(addressValue),
            weAddressServiceApiFeign.getAddressValue(
                address = address,
                key = "some-key"
            ),
        )
    }

    @Test
    fun `should get address values`() {
        val addressValues = listOf(
            DataEntryDto(
                type = "integer",
                key = "some-key",
                value = 100,
            ),
            DataEntryDto(
                type = "string",
                key = "another-key",
                value = "testValue",
            ),
        )
        assertEquals(
            addressValues,
            weAddressServiceApiFeign.getAddressValues(address)
        )
    }

    @Test
    fun `should sign message`() {
        val request = SignMessageRequestDto(
            message = "message",
            password = "password"
        )
        val response = SignMessageResponseDto(
            message = "59Su1K4KSU",
            publicKey = publicKey,
            signature = signature,
        )
        assertEquals(
            response,
            weAddressServiceApiFeign.signMessage(
                address = address,
                request = request,
            )
        )
    }

    @Test
    fun `should verify message signature`() {
        val request = VerifyMessageSignatureRequestDto(
            message = "59Su1K4KSU",
            publicKey = publicKey,
            signature = signature,
        )
        val response = VerifyMessageSignatureResponseDto(valid = true)
        assertEquals(
            response,
            weAddressServiceApiFeign.verifyMessageSignature(
                address = address,
                request = request,
            )
        )
    }

    @Test
    fun `should throw InvalidPublicKeyException`() {
        assertThrows<InvalidPublicKeyException> {
            weAddressServiceApiFeign.getAddressByPublicKey("invalid-public-key")
        }.apply {
            assertEquals(NodeErrorCode.INVALID_PUBLIC_KEY.code, nodeError.error)
            assertEquals(
                "invalid public key: Can't create public key from string 'invalid-public-key': " +
                    "Unable to create public key: null",
                nodeError.message
            )
        }
    }

    @Test
    fun `should throw InvalidPasswordException`() {
        val request = SignMessageRequestDto(
            message = "message",
            password = "incorrect-password"
        )
        assertThrows<InvalidPasswordException> {
            weAddressServiceApiFeign.signMessage(
                address = address,
                request = request,
            )
        }.apply {
            assertEquals(NodeErrorCode.INVALID_PASSWORD.code, nodeError.error)
            assertEquals(
                "no private key for sender address in wallet or provided password is incorrect",
                nodeError.message
            )
        }
    }
}
