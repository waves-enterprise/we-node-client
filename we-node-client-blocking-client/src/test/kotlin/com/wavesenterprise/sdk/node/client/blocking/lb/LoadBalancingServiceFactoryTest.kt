package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.blocking.policyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.blocking.sendDataRequest
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeyRequest
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.exception.NodeError
import com.wavesenterprise.sdk.node.exception.NodeErrorCode
import com.wavesenterprise.sdk.node.exception.NodeInternalServerErrorException
import com.wavesenterprise.sdk.node.exception.NodeServiceUnavailableException
import com.wavesenterprise.sdk.node.exception.specific.ContractNotFoundException
import com.wavesenterprise.sdk.node.exception.specific.PolicyItemDataIsMissingException
import io.mockk.Called
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.OffsetDateTime
import java.util.Optional

/**
 * Tests LoadBalancingServiceFactory built with [DefaultRetryStrategy] so the exceptions thrown by mocked
 * services are chosen according to the [DefaultRetryStrategy] implementation.
 */
class LoadBalancingServiceFactoryTest {

    private val lbServiceFactoryBuilder: LbServiceFactoryBuilder = LbServiceFactoryBuilder.builder()

    @Test
    fun `should balance between different clients (no params)`() {
        val mockkAddressService1 = mockkAddressService().also {
            every {
                it.getAddresses()
            } returns listOf()
        }
        val client1 = "1" to mockkNodeBlockingServiceFactory(addressService = mockkAddressService1)
        val mockkAddressService2 = mockkAddressService().also {
            every {
                it.getAddresses()
            } returns listOf()
        }
        val client2 = "2" to mockkNodeBlockingServiceFactory(addressService = mockkAddressService2)

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .build(mapOf(client1, client2))

        repeat(TESTS) { lb.addressService().getAddresses() }

        verify(atLeast = 50) { mockkAddressService1.getAddresses() }
        verify(atLeast = 50) { mockkAddressService2.getAddresses() }
    }

    @Test
    fun `should balance between different clients (one param)`() {
        val mockkContractService1 = mockkContractService().also {
            every {
                it.getContractKey(any())
            } returns Optional.empty()
        }
        val client1 = "1" to mockkNodeBlockingServiceFactory(contractService = mockkContractService1)
        val mockkContractService2 = mockkContractService().also {
            every {
                it.getContractKey(any())
            } returns Optional.empty()
        }
        val client2 = "2" to mockkNodeBlockingServiceFactory(contractService = mockkContractService2)

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .build(mapOf(client1, client2))
        val contractKeyRequest = (ContractKeyRequest(contractId = ContractId.fromBase58(""), key = ""))
        repeat(TESTS) {
            lb.contractService().getContractKey(contractKeyRequest)
        }

        verify(atLeast = 50) { mockkContractService1.getContractKey(contractKeyRequest) }
        verify(atLeast = 50) { mockkContractService2.getContractKey(contractKeyRequest) }
    }

    @Test
    fun `should balance calls to one client when other fails`() {
        val mockkContractService1 = mockkContractService().also {
            every {
                it.getContractKey(any())
            } returns Optional.empty()
        }
        val client1 = "1" to mockkNodeBlockingServiceFactory(contractService = mockkContractService1)
        val client2 = "2" to nodeNotAvailableFailingMockClient()

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .build(mapOf(client1, client2))

        val contractKeyRequest = ContractKeyRequest(contractId = ContractId.fromBase58(""), key = "")
        repeat(TESTS) { lb.contractService().getContractKey(contractKeyRequest) }

        verify(atLeast = TESTS) { mockkContractService1.getContractKey(contractKeyRequest) }
    }

    @Test
    fun `should balance calls to one client when other fails with retryable`() {
        val mockkContractService1 = mockkContractService().also {
            every {
                it.getContractKey(any())
            } returns Optional.empty()
        }
        val client1 = "1" to mockkNodeBlockingServiceFactory(contractService = mockkContractService1)
        val client2 = "2" to retryableFailingMockClient()

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .build(mapOf(client1, client2))

        val contractKeyRequest = ContractKeyRequest(contractId = ContractId.fromBase58(""), key = "")
        repeat(TESTS) { lb.contractService().getContractKey(contractKeyRequest) }

        verify(atLeast = TESTS) { mockkContractService1.getContractKey(contractKeyRequest) }
    }

    @Test
    fun `should propagate fail when both clients fail`() {
        val client1 = "1" to nodeNotAvailableFailingMockClient()
        val client2 = "2" to nodeNotAvailableFailingMockClient()

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .build(mapOf(client1, client2))

        val contractKeyRequest = ContractKeyRequest(contractId = ContractId.fromBase58(""), key = "")
        assertThrows<NodeServiceUnavailableException> { lb.contractService().getContractKey(contractKeyRequest) }
    }

    @Test
    fun `should propagate fail when client fails with business error`() {
        val client1 = "1" to businessFailingMockClient()

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .build(mapOf(client1))

        val contractKeyRequest = ContractKeyRequest(contractId = ContractId.fromBase58(""), key = "")
        val ex = assertThrows<ContractNotFoundException> { lb.contractService().getContractKey(contractKeyRequest) }
        assertEquals(600, ex.nodeError.error)
    }

    @Test
    fun `should balance info from privacy calls to clients with correct owner`() {
        val mockkPrivacyService1 = mockkPrivacyService().also {
            every {
                it.recipients(any())
            } returns policies["AB"]!!
        }
        val mockkPrivacyService2 = mockkPrivacyService().also {
            every {
                it.recipients(any())
            } returns policies["AB"]!!
        }
        val client1 = "1" to mockkNodeBlockingServiceFactory(
            addresses = listOf("A", "B"),
            privacyService = mockkPrivacyService1,
        )
        val client2 = "2" to mockkNodeBlockingServiceFactory(
            addresses = listOf("B", "C"),
            privacyService = mockkPrivacyService2,
        )
        val (nodeAlis3, client3) = "3" to mockkNodeBlockingServiceFactory(
            addresses = listOf("C", "D")
        )

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .build(mapOf(client1, client2, nodeAlis3 to client3))

        val policyId = "AB"
        val dataHash = "dataHash"
        val policyItemRequest = PolicyItemRequest(PolicyId(TxId.fromBase58(policyId)), Hash(dataHash.toByteArray()))
        repeat(TESTS) { lb.privacyService().info(policyItemRequest) }

        verify(atLeast = 50) { mockkPrivacyService1.info(policyItemRequest) }
        verify(atLeast = 50) { mockkPrivacyService2.info(policyItemRequest) }
        verify { client3.privacyService() wasNot Called }
    }

    @Test
    fun `should balance getDataFromPrivacy calls to clients with success getInfoFromPrivacy previous calls`() {
        val mockkPrivacyService1 = mockkPrivacyService().also {
            every {
                it.recipients(any())
            } returns policies["AB"]!!
        }
        val client1 = "1" to mockkNodeBlockingServiceFactory(
            addresses = listOf("A", "B"),
            privacyService = mockkPrivacyService1,
        )
        val mockkPrivacyService2 = mockkPrivacyService().also {
            every {
                it.recipients(any())
            } returns policies["AB"]!!
        }
        val client2 = "2" to mockkNodeBlockingServiceFactory(
            addresses = listOf("B", "C"),
            privacyService = mockkPrivacyService2,
        )
        val mockkPrivacyService3 = mockkPrivacyService().also {
            every {
                it.info(any())
            } throws PolicyItemDataIsMissingException(
                nodeError = NodeError(
                    NodeErrorCode.POLICY_ITEM_DATA_IS_MISSING.code,
                    ""
                ),
                cause = Exception()
            )
        }
        val client3 = "3" to mockkNodeBlockingServiceFactory(
            addresses = listOf("C", "D"),
            privacyService = mockkPrivacyService3,
        )

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .build(mapOf(client1, client2, client3))

        val policyItemRequest = PolicyItemRequest(PolicyId(TxId.fromBase58("ABC")), Hash("dataHash".toByteArray()))
        repeat(TESTS) {
            lb.privacyService().info(policyItemRequest)
            lb.privacyService().data(policyItemRequest)
        }

        verify(atLeast = 50) { mockkPrivacyService1.data(policyItemRequest) }
        verify(atLeast = 50) { mockkPrivacyService2.data(policyItemRequest) }
        verify { mockkPrivacyService3.data(policyItemRequest) wasNot Called }
    }

    @Test
    fun `should balance send data calls to clients with correct owner despite system failure`() {
        val (nodeAlis1, client1) = "1" to nodeNotAvailableFailingMockClient(
            addresses = listOf("A", "B"),
        )
        val mockkPrivacyService2 = mockkPrivacyService().also {
            every {
                it.recipients(any())
            } returns policies["AB"]!!
        }
        val client2 = "2" to mockkNodeBlockingServiceFactory(
            addresses = listOf("B", "C"),
            privacyService = mockkPrivacyService2,
        )
        val (nodeAlis3, client3) = "3" to mockkNodeBlockingServiceFactory(
            addresses = listOf("C", "D"),
        )

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(
                nodeCredentialsProvider(
                    mapOf(Address.fromBase58("B") to Password("password"))
                )
            )
            .build(mapOf(nodeAlis1 to client1, client2, nodeAlis3 to client3))

        val dto = sendDataRequest()
        repeat(TESTS) { lb.privacyService().sendData(dto) }

        verify { client1.privacyService() wasNot Called }
        verify(atLeast = 50) { mockkPrivacyService2.sendData(any()) }
        verify { client3.privacyService() wasNot Called }
    }

    @Test
    fun `should balance getInfoFromPrivacy calls to clients with correct owner`() {
        val mockkPrivacyService1 = mockkPrivacyService().also {
            every {
                it.info(any())
            } returns Optional.of(policyItemInfoResponse())
        }
        val client1 = "1" to mockkNodeBlockingServiceFactory(
            addresses = listOf("A", "B"),
            privacyService = mockkPrivacyService1,
        )
        val mockkPrivacyService2 = mockkPrivacyService().also {
            every {
                it.info(any())
            } returns Optional.of(policyItemInfoResponse())
        }
        val client2 = "2" to mockkNodeBlockingServiceFactory(
            addresses = listOf("B", "C"),
            privacyService = mockkPrivacyService2,
        )
        val (nodeAlis3, client3) = "3" to mockkNodeBlockingServiceFactory(
            addresses = listOf("C", "D"),
        )

        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .build(mapOf(client1, client2, nodeAlis3 to client3))

        val policyItemRequest = PolicyItemRequest(
            policyId = PolicyId(TxId.fromBase58("AB")),
            dataHash = Hash("dataHash".toByteArray()),
        )
        repeat(TESTS) { lb.privacyService().info(policyItemRequest) }

        verify(atLeast = 50) { mockkPrivacyService1.info(policyItemRequest) }
        verify(atLeast = 50) { mockkPrivacyService2.info(policyItemRequest) }
        verify { client3.privacyService() wasNot Called }
    }

    @Test
    fun `should remove client from sharding after system error & put it back after success response`() {
        val mockkAddressService1 = mockkAddressService().also {
            every {
                it.getAddresses()
            } returns listOf()
            every {
                it.getAddressValues(any())
            } returns listOf()
        }
        val client1 = "1" to mockkNodeBlockingServiceFactory(addressService = mockkAddressService1)
        val mockkAddressService2 = mockkAddressService().also {
            every {
                it.getAddresses()
            } returns listOf()
        }
        val (nodeAlias2, client2) = "2" to mockkNodeBlockingServiceFactory(addressService = mockkAddressService2)

        val defaultNodeCircuitBreaker1 = DefaultNodeCircuitBreaker()
        val defaultNodeCircuitBreaker2 = DefaultNodeCircuitBreaker()
        val circuitBreaker = DefaultCircuitBreaker(
            circuitBreakerProperties = CircuitBreakerProperties(),
            nodeCircuitBreakers = mapOf("1" to defaultNodeCircuitBreaker1, "2" to defaultNodeCircuitBreaker2)
        )
        val lb = lbServiceFactoryBuilder
            .nodeCredentialsProvider(nodeCredentialsProvider())
            .circuitBreaker(circuitBreaker)
            .build(mapOf(client1, nodeAlias2 to client2))

        repeat(TESTS) { lb.addressService().getAddresses() }

        verify(atLeast = 50) { mockkAddressService1.getAddresses() }
        verify(atLeast = 50) { mockkAddressService2.getAddresses() }

        assertTrue(circuitBreaker.isClosed(nodeAlias2))

        every {
            mockkAddressService2.getAddressValues(any())
        } throws NodeServiceUnavailableException(cause = Exception())

        val address = "address"
        repeat(TESTS) { lb.addressService().getAddressValues(address) }
        verify(atLeast = 50) { mockkAddressService1.getAddressValues(address) }
        verify(atLeast = 1) { mockkAddressService2.getAddressValues(address) }
        assertFalse(circuitBreaker.isClosed(nodeAlias2))

        defaultNodeCircuitBreaker2.breakUntil = OffsetDateTime.MIN
        assertTrue(circuitBreaker.isClosed(nodeAlias2))

        repeat(TESTS) { lb.addressService().getAddresses() }
        verify(atLeast = 50) { mockkAddressService1.getAddresses() }
        verify(atLeast = 50) { mockkAddressService2.getAddresses() }
        assertTrue(circuitBreaker.isClosed(nodeAlias2))
    }

    @Test
    fun `should increase sequentialErrorCount only if current period quarantineUntil is ended`() {
        val threadCount = 25
        val expectedSequentialErrorCount = 1

        (1..TESTS / 10).forEach { _ ->
            val mockkAddressService1 = mockkAddressService().also {
                every {
                    it.getAddresses()
                } returns listOf()
            }
            val client1 = "1" to mockkNodeBlockingServiceFactory(addressService = mockkAddressService1)
            val mockkAddressService2 = mockkAddressService().also {
                every {
                    it.getAddresses()
                } throws NodeInternalServerErrorException(cause = Exception())
            }
            val client2 = "2" to mockkNodeBlockingServiceFactory(addressService = mockkAddressService2)

            val defaultNodeCircuitBreaker1 = DefaultNodeCircuitBreaker()
            val defaultNodeCircuitBreaker2 = DefaultNodeCircuitBreaker()
            val circuitBreaker = DefaultCircuitBreaker(
                circuitBreakerProperties = CircuitBreakerProperties(),
                nodeCircuitBreakers = mapOf("1" to defaultNodeCircuitBreaker1, "2" to defaultNodeCircuitBreaker2)
            )
            val lb = lbServiceFactoryBuilder
                .nodeCredentialsProvider(nodeCredentialsProvider())
                .circuitBreaker(circuitBreaker)
                .build(mapOf(client1, client2))

            (1..threadCount).map {
                Thread { lb.addressService().getAddresses() }
                    .apply { start() }
            }.forEach { it.join() }

            verify(atLeast = threadCount) { mockkAddressService1.getAddresses() }
            verify(atLeast = 1) { mockkAddressService2.getAddresses() }

            assertEquals(0, defaultNodeCircuitBreaker1.sequentialErrorCount.toInt())
            assertEquals(expectedSequentialErrorCount, defaultNodeCircuitBreaker2.sequentialErrorCount.toInt())
        }
    }

    companion object {
        private const val TESTS = 1000
        private val policies = mapOf("AB" to listOf(Address.fromBase58("A"), Address.fromBase58("B")))
    }
}
