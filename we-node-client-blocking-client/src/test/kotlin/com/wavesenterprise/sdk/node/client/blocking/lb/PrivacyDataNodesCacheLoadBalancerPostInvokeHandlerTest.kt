package com.wavesenterprise.sdk.node.client.blocking.lb

import com.github.benmanes.caffeine.cache.Caffeine
import com.wavesenterprise.sdk.node.client.blocking.cache.CaffeineLoadingCache
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.atomicSignRequest
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.atomicTx
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.policyId
import com.wavesenterprise.sdk.node.test.data.TestDataFactory.Companion.policyItemInfoResponse
import io.mockk.every
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Optional

class PrivacyDataNodesCacheLoadBalancerPostInvokeHandlerTest {

    private val lbServiceFactoryBuilder: LbServiceFactoryBuilder = LbServiceFactoryBuilder.builder()

    @Test
    fun `should cache node alias by privacy data key when call info from privacy`() {
        val address = TestDataFactory.address()
        val privacyDataNodesCache = PrivacyDataNodesCache(CaffeineLoadingCache(Caffeine.newBuilder().build()))
        val someDataHash = Hash("test-data".toByteArray())
        val policyId = policyId()
        val client = "node-alias" to mockkNodeBlockingServiceFactory(
            addressService = mockkAddressService().also {
                every {
                    it.getAddresses()
                } returns listOf(address)
            },
            privacyService = mockkPrivacyService().also {
                every {
                    it.info(any())
                } returns Optional.of(
                    policyItemInfoResponse(
                        dataHash = someDataHash,
                        policyId = policyId,
                    ),
                )
            },
        )
        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .privacyDataNodesCache(privacyDataNodesCache)
            .build(mapOf(client))

        lb.privacyService().info(TestDataFactory.policyItemRequest(policyId = policyId, dataHash = someDataHash))

        privacyDataNodesCache.get(policyId = policyId, policyDataHash = someDataHash).apply {
            assertTrue(this.contains(client.first))
        }
    }

    @Test
    fun `should cache node alias by privacy data key when broadcast PolicyDataHashTx in atomic`() {
        val address = TestDataFactory.address()
        val privacyDataNodesCache = PrivacyDataNodesCache(CaffeineLoadingCache(Caffeine.newBuilder().build()))
        val someDataHash = Hash("test-data".toByteArray())
        val policyId = policyId()
        val client = "node-alias" to mockkNodeBlockingServiceFactory(
            addressService = mockkAddressService().also {
                every {
                    it.getAddresses()
                } returns listOf(address)
            },
            txService = mockkTxService().also {
                every {
                    it.broadcast(any())
                } returns atomicTx(
                    senderAddress = address,
                    txs = listOf(
                        TestDataFactory.policyDataHashTx(
                            policyId = policyId,
                            dataHash = someDataHash,
                            senderAddress = address,
                        ),
                    ),
                )
            },
        )
        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .privacyDataNodesCache(privacyDataNodesCache)
            .build(mapOf(client))

        lb.txService().broadcast(atomicTx())

        privacyDataNodesCache.get(policyId = policyId, policyDataHash = someDataHash).apply {
            assertTrue(this.contains(client.first))
        }
    }

    @Test
    fun `should cache node alias by privacy data key when signAndBroadcast PolicyDataHashTx in atomic`() {
        val signRequestCaptor = slot<AtomicSignRequest>()
        val address = TestDataFactory.address()
        val privacyDataNodesCache = PrivacyDataNodesCache(CaffeineLoadingCache(Caffeine.newBuilder().build()))
        val someDataHash = Hash("test-data".toByteArray())
        val policyId = policyId()
        val client = "node-alias" to mockkNodeBlockingServiceFactory(
            addressService = mockkAddressService().also {
                every {
                    it.getAddresses()
                } returns listOf(address)
            },
            txService = mockkTxService().also {
                every {
                    it.signAndBroadcast(capture(signRequestCaptor))
                } returns atomicTx(
                    senderAddress = address,
                    txs = listOf(
                        TestDataFactory.policyDataHashTx(
                            policyId = policyId,
                            dataHash = someDataHash,
                            senderAddress = address,
                        ),
                    ),
                )
            },
        )
        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .privacyDataNodesCache(privacyDataNodesCache)
            .build(mapOf(client))

        lb.txService().signAndBroadcast(atomicSignRequest(senderAddress = address))

        privacyDataNodesCache.get(policyId = policyId, policyDataHash = someDataHash).apply {
            assertTrue(this.contains(client.first))
        }
    }

    @Test
    fun `should cache node alias by privacy data key when send data to privacy`() {
        val address = TestDataFactory.address()
        val privacyDataNodesCache = PrivacyDataNodesCache(CaffeineLoadingCache(Caffeine.newBuilder().build()))
        val someDataHash = Hash("test-data".toByteArray())
        val policyId = policyId()
        val client = "node-alias" to mockkNodeBlockingServiceFactory(
            addressService = mockkAddressService().also {
                every {
                    it.getAddresses()
                } returns listOf(address)
            },
            privacyService = mockkPrivacyService().also {
                every {
                    it.sendData(any())
                } returns TestDataFactory.policyDataHashTx(
                    policyId = policyId,
                    dataHash = someDataHash,
                )
            },
        )
        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .privacyDataNodesCache(privacyDataNodesCache)
            .build(mapOf(client))

        lb.privacyService().sendData(TestDataFactory.sendDataRequest(policyId = policyId, dataHash = someDataHash))

        privacyDataNodesCache.get(policyId = policyId, policyDataHash = someDataHash).apply {
            assertTrue(this.contains(client.first))
        }
    }
}
