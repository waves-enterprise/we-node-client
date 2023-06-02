package com.wavesenterprise.sdk.node.client.feign.privacy

import com.wavesenterprise.sdk.node.client.http.privacy.PolicyItemInfoResponseDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.PolicyDataHashTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.PolicyDataHashTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash.Companion.base58StrHash
import com.wavesenterprise.sdk.node.domain.privacy.Data.Companion.data
import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.specific.PolicyItemDataIsMissingException
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.dataHash
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.policyDataHashTx
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.policyId
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.policyItemInfoResponse
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.policyItemRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.sendDataRequest
import com.wavesenterprise.sdk.node.test.data.Util.Companion.randomBytesFromUUID
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.lang.Exception
import java.util.Optional

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeignPrivacyServiceTest {

    private val wePrivacyServiceApiFeign: WePrivacyServiceApiFeign = mockk()

    lateinit var feignPrivacyService: FeignPrivacyService

    @BeforeEach
    fun setUp() {
        feignPrivacyService = FeignPrivacyService(wePrivacyServiceApiFeign)
    }

    @Test
    fun `should return policy data hash tx when send data`() {
        val expectedPolicyDataHashTxDto = policyDataHashTx().toDto()
        val sendDataRequest = sendDataRequest()
        every {
            wePrivacyServiceApiFeign.sendDataToPrivacy(any())
        } returns expectedPolicyDataHashTxDto
        val policyDataHashTx = feignPrivacyService.sendData(sendDataRequest)
        assertEquals(expectedPolicyDataHashTxDto.toDomain(), policyDataHashTx)
    }

    @Test
    fun `should return empty optional of policy item info when it does not exist`() {
        val policyItemRequest = policyItemRequest()
        every {
            wePrivacyServiceApiFeign.getPolicyItemInfo(any(), any())
        } throws PolicyItemDataIsMissingException(
            nodeError = NodeError(
                error = 612,
                message = "The requested policy ${policyItemRequest.policyId.asBase58String()} does not exist",
            ),
            cause = Exception(),
        )

        assertFalse(feignPrivacyService.info(request = policyItemRequest()).isPresent)
    }

    @Test
    fun `should return policy item info when it exist`() {
        val expectedPolicyItemInfo = policyItemInfoResponse()
        val policyItemInfoResponseDto = expectedPolicyItemInfo.toDto()
        every {
            wePrivacyServiceApiFeign.getPolicyItemInfo(any(), any())
        } returns policyItemInfoResponseDto

        val info = feignPrivacyService.info(policyItemRequest())

        assertEquals(Optional.of(expectedPolicyItemInfo), info)
    }

    @Test
    fun `should return recipients by policy id`() {
        val expectedRecipients = listOf(
            "DP5MggKC8GJuLZshCVNSYwBtE6WTRtMM1YPPdcmwbuNg",
            "C2HM9q3QzGSBydnCA4GMcf3cFnTaSuwaWXVtsCSTSmZW",
        )
        every {
            wePrivacyServiceApiFeign.getPolicyRecipients(any())
        } returns expectedRecipients

        val recipients = feignPrivacyService.recipients(policyId = policyId())
        assertEquals(expectedRecipients.map { Address.fromBase58(it) }, recipients)
    }

    @Test
    fun `should return owners by policy id`() {
        val expectedOwners = listOf(
            "DP5MggKC8GJuLZshCVNSYwBtE6WTRtMM1YPPdcmwbuNg",
            "C2HM9q3QzGSBydnCA4GMcf3cFnTaSuwaWXVtsCSTSmZW",
        )
        every {
            wePrivacyServiceApiFeign.getPolicyOwners(any())
        } returns expectedOwners

        val owners = feignPrivacyService.owners(policyId())
        assertEquals(expectedOwners.map { Address.fromBase58(it) }, owners)
    }

    @Test
    fun `should return hashes by policy id`() {
        dataHash()
        val expectedHashes = listOf(
            dataHash().asBase58String(),
            dataHash().asBase58String(),
        )
        every {
            wePrivacyServiceApiFeign.getPolicyHashes(any())
        } returns expectedHashes

        val hashes = feignPrivacyService.hashes(policyId())
        assertEquals(expectedHashes.map { it.base58StrHash }, hashes)
    }

    @Test
    fun `should return data by policy id`() {
        val expectedData = randomBytesFromUUID()

        every {
            wePrivacyServiceApiFeign.getDataFromPrivacy(any(), any())
        } returns expectedData
        val data = feignPrivacyService.data(policyItemRequest())
        assertEquals(Optional.of(expectedData.data), data)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should return t`(
        expectedValue: Boolean,
    ) {
        val expectedData = if (expectedValue) randomBytesFromUUID() else ByteArray(0)

        every {
            wePrivacyServiceApiFeign.getDataFromPrivacy(any(), any())
        } returns expectedData
        val exist = feignPrivacyService.exists(policyItemRequest())
        assertEquals(expectedValue, exist)
    }
}
