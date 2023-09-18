package com.wavesenterprise.sdk.node.client.blocking.lb

import com.github.benmanes.caffeine.cache.Caffeine
import com.wavesenterprise.sdk.node.client.blocking.cache.CaffeineLoadingCache
import com.wavesenterprise.sdk.node.client.blocking.lb.exception.UnknownUpdatePolicyOpTypeException
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PolicyId.Companion.policyId
import com.wavesenterprise.sdk.node.domain.sign.CreatePolicySignRequest
import com.wavesenterprise.sdk.node.domain.sign.UpdatePolicySignRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.address
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.createPolicySignRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.createPolicyTx
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.policyId
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.updatePolicySignRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.updatePolicyTx
import io.mockk.every
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RecipientsCacheLoadBalancerPostInvokeHandlerTest {

    private val lbServiceFactoryBuilder: LbServiceFactoryBuilder = LbServiceFactoryBuilder.builder()

    @Test
    fun `should cache recipients when lb sign CreatePolicySignRequest`() {
        val address = address()
        val signRequestCaptor = slot<CreatePolicySignRequest>()
        val recipientsCache = CaffeineLoadingCache(Caffeine.newBuilder().build<PolicyId, Set<Address>>())
        val recipients1 = listOf(address(), address())
        val client = "1" to mockkNodeBlockingServiceFactory(
            txService = mockkTxService().also {
                every { it.sign(capture(signRequestCaptor)) } returns createPolicyTx(recipients = recipients1)
            },
            addressService = mockkAddressService().also {
                every {
                    it.getAddresses()
                } returns listOf(address)
            }
        )
        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .recipientsCache(recipientsCache)
            .build(mapOf(client))

        val createPolicyTx = lb.txService().sign(createPolicySignRequest(senderAddress = address))

        assertEquals(recipients1.toMutableSet(), recipientsCache.getIfPresent(createPolicyTx.id.policyId))
    }

    @Test
    fun `should cache recipients when lb sign UpdatePolicySignRequest with op type equals ADD`() {
        val address = address()
        val signRequestCaptor = slot<UpdatePolicySignRequest>()
        val existPolicyId = policyId()
        val existRecipients = mutableSetOf(address(), address())
        val recipientsCache = CaffeineLoadingCache(Caffeine.newBuilder().build<PolicyId, Set<Address>>())
        recipientsCache.put(existPolicyId, existRecipients)
        val recipients = listOf(address(), address())
        val client = "1" to mockkNodeBlockingServiceFactory(
            txService = mockkTxService().also {
                every { it.sign(capture(signRequestCaptor)) } returns updatePolicyTx(
                    recipients = recipients,
                    policyId = existPolicyId,
                    opType = OpType.ADD,
                )
            },
            addressService = mockkAddressService().also {
                every {
                    it.getAddresses()
                } returns listOf(address)
            }
        )
        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .recipientsCache(recipientsCache)
            .build(mapOf(client))

        val updatePolicyTx = lb.txService().sign(
            updatePolicySignRequest(
                senderAddress = address,
                policyId = existPolicyId,
            )
        )
        assertEquals(
            (recipients + existRecipients).toSet(),
            recipientsCache.getIfPresent(updatePolicyTx.policyId)
        )
    }

    @Test
    fun `should cache recipients when lb sign UpdatePolicySignRequest with op type equals REMOVE`() {
        val address = address()
        val signRequestCaptor = slot<UpdatePolicySignRequest>()
        val existPolicyId = policyId()
        val recipientToRemove = address()
        val existRecipients = mutableSetOf(recipientToRemove)
        val recipientsCache = CaffeineLoadingCache(Caffeine.newBuilder().build<PolicyId, Set<Address>>())
        recipientsCache.put(existPolicyId, existRecipients)
        val recipients = listOf(recipientToRemove)
        val client = "1" to mockkNodeBlockingServiceFactory(
            txService = mockkTxService().also {
                every { it.sign(capture(signRequestCaptor)) } returns updatePolicyTx(
                    recipients = recipients,
                    policyId = existPolicyId,
                    opType = OpType.REMOVE,
                )
            },
            addressService = mockkAddressService().also {
                every {
                    it.getAddresses()
                } returns listOf(address)
            }
        )
        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .recipientsCache(recipientsCache)
            .build(mapOf(client))

        lb.txService().sign(
            updatePolicySignRequest(
                senderAddress = address,
                policyId = existPolicyId,
            )
        )
        assertTrue(recipientsCache.getIfPresent(existPolicyId)!!.isEmpty())
    }

    @Test
    fun `should cache recipients when lb sign UpdatePolicySignRequest with op type equals UNKNOWN`() {
        val address = address()
        val signRequestCaptor = slot<UpdatePolicySignRequest>()
        val existPolicyId = policyId()
        val existRecipients = mutableSetOf(address(), address())
        val recipientsCache = CaffeineLoadingCache(Caffeine.newBuilder().build<PolicyId, Set<Address>>())
        recipientsCache.put(existPolicyId, existRecipients)
        val client = "test" to mockkNodeBlockingServiceFactory(
            txService = mockkTxService().also {
                every { it.sign(capture(signRequestCaptor)) } returns updatePolicyTx(
                    policyId = existPolicyId,
                    opType = OpType.UNKNOWN,
                )
            },
            addressService = mockkAddressService().also {
                every {
                    it.getAddresses()
                } returns listOf(address)
            }
        )
        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .recipientsCache(recipientsCache)
            .build(mapOf(client))

        assertThrows<UnknownUpdatePolicyOpTypeException> {
            lb.txService().sign(
                updatePolicySignRequest(
                    senderAddress = address,
                    policyId = existPolicyId,
                )
            )
        }
    }
}
