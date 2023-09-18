package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.atomic.cache.contract.info.ThreadLocalContractInfoCacheManager
import com.wavesenterprise.sdk.atomic.manager.AtomicAwareContextManager
import com.wavesenterprise.sdk.atomic.manager.AtomicAwareContextManagerHook
import com.wavesenterprise.sdk.atomic.manager.ContractInfoCacheContextManagerHook
import com.wavesenterprise.sdk.atomic.manager.ContractInfoCacheManager
import com.wavesenterprise.sdk.atomic.manager.ThreadLocalAtomicAwareContextManagerWithHook
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.contract.ContractId.Companion.contractId
import com.wavesenterprise.sdk.node.domain.contract.ContractInfo
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.ContractTx.Companion.contractId
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.test.data.TestDataFactory
import com.wavesenterprise.sdk.tx.signer.TxSigner
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Optional

class AtomicAwareNodeBlockingServiceFactoryTest {

    private lateinit var atomicAwareNodeBlockingServiceFactory: AtomicAwareNodeBlockingServiceFactory
    private lateinit var atomicAwareContextManager: AtomicAwareContextManager
    private lateinit var contractInfoCacheManager: ContractInfoCacheManager
    private lateinit var atomicAwareContextManagerHook: AtomicAwareContextManagerHook

    private val nodeBlockingServiceFactory: NodeBlockingServiceFactory = mockk()
    private val txService: TxService = mockk()
    private val contractService: ContractService = mockk()
    private val privacyService: PrivacyService = mockk()
    private val txSigner: TxSigner = mockk()

    @BeforeEach
    fun setUp() {
        every { txService.broadcast(any()) } returns TestDataFactory.callContractTx()
        every { nodeBlockingServiceFactory.txService() } returns txService
        every { nodeBlockingServiceFactory.contractService() } returns contractService
        every { nodeBlockingServiceFactory.privacyService() } returns privacyService
        contractInfoCacheManager = ThreadLocalContractInfoCacheManager()
        atomicAwareContextManagerHook = ContractInfoCacheContextManagerHook(contractInfoCacheManager)
        atomicAwareContextManager = ThreadLocalAtomicAwareContextManagerWithHook(atomicAwareContextManagerHook)
        atomicAwareNodeBlockingServiceFactory =
            AtomicAwareNodeBlockingServiceFactory(
                nodeBlockingServiceFactory = nodeBlockingServiceFactory,
                atomicAwareContextManager = atomicAwareContextManager,
                contractInfoCacheManager = contractInfoCacheManager,
            ) { txSigner }
    }

    @Test
    fun `should add and broadcast txs in atomic tx`() {
        atomicAwareContextManager.beginAtomic()
        val expectedTxsInAtomic = mutableListOf<Tx>().apply {
            repeat(3) {
                add(atomicAwareNodeBlockingServiceFactory.txService().broadcast(TestDataFactory.callContractTx()))
            }
        }
        val atomicTxs = atomicAwareContextManager.commitAtomic().txs

        assertEquals(expectedTxsInAtomic, atomicTxs)
    }

    @Test
    fun `should clear context after every commit(broadcast) atomic tx`() {
        atomicAwareContextManager.beginAtomic()
        val expectedAtomicTx1: CallContractTx = atomicAwareNodeBlockingServiceFactory.txService().broadcast(
            tx = TestDataFactory.callContractTx(),
        )
        val atomicTx1: AtomicSignRequest = atomicAwareContextManager.commitAtomic()
        assertEquals(1, atomicTx1.txs.size)
        assertEquals(expectedAtomicTx1, atomicTx1.txs[0])

        atomicAwareContextManager.beginAtomic()
        val expectedAtomicTx2: CallContractTx = atomicAwareNodeBlockingServiceFactory.txService().broadcast(
            tx = TestDataFactory.callContractTx(),
        )
        val atomicTx2: AtomicSignRequest = atomicAwareContextManager.commitAtomic()
        assertEquals(1, atomicTx2.txs.size)
        assertEquals(expectedAtomicTx2, atomicTx2.txs[0])
    }

    @Test
    fun `should cache ContractInfo when broadcast CreateContractTx`() {
        atomicAwareContextManager.beginAtomic()
        val createContractTx = TestDataFactory.createContractTx()

        every { contractService.getContractInfo(createContractTx.id.contractId) } returns Optional.empty()
        atomicAwareNodeBlockingServiceFactory.txService().broadcast(
            tx = createContractTx,
        )

        contractInfoCacheManager
            .getCache()
            .get(createContractTx.id.contractId)!!
            .also {
                assertEquals(it.id, createContractTx.contractId())
                assertEquals(it.image, createContractTx.image)
                assertEquals(it.imageHash, createContractTx.imageHash)
                assertEquals(it.version, ContractVersion(1))
                assertEquals(it.active, true)
            }
    }

    @Test
    fun `should use ContractInfo cache when call getContractInfo`() {
        val expectedContractInfo: ContractInfo = TestDataFactory.contractInfo()
        contractInfoCacheManager.getCache().put(
            contractId = expectedContractInfo.id,
            contractInfo = expectedContractInfo,
        )
        every { contractService.getContractInfo(any()) } returns Optional.empty()

        atomicAwareNodeBlockingServiceFactory.contractService().getContractInfo(
            contractId = expectedContractInfo.id,
        ).also {
            assertEquals(it.get(), expectedContractInfo)
        }
    }

    @Test
    fun `should add atomic badge to send data request if necessary`() {
        val addressFromTxSigner = TestDataFactory.address()
        every { txSigner.getSignerAddress() } returns addressFromTxSigner
        val sendDataRequestCaptor = slot<SendDataRequest>()
        every { privacyService.sendData(capture(sendDataRequestCaptor)) } returns TestDataFactory.policyDataHashTx()
        val sendDataRequest = TestDataFactory.sendDataRequest(
            broadcastTx = false,
            senderAddress = TestDataFactory.address(),
            atomicBadge = null,
        )
        atomicAwareNodeBlockingServiceFactory.privacyService().sendData(sendDataRequest)

        sendDataRequestCaptor.captured.also {
            assertNotNull(it.atomicBadge)
            assertEquals(addressFromTxSigner, it.atomicBadge!!.trustedSender)
        }
    }

    @Test
    fun `shouldn't add atomic badge to send data request if not necessary`() {
        val addressFromTxSigner = TestDataFactory.address()
        every { txSigner.getSignerAddress() } returns addressFromTxSigner
        val sendDataRequestCaptor = slot<SendDataRequest>()
        every { privacyService.sendData(capture(sendDataRequestCaptor)) } returns TestDataFactory.policyDataHashTx()
        val sendDataRequest = TestDataFactory.sendDataRequest(
            broadcastTx = false,
            senderAddress = addressFromTxSigner,
            atomicBadge = null,
        )
        atomicAwareNodeBlockingServiceFactory.privacyService().sendData(sendDataRequest)

        sendDataRequestCaptor.captured.also {
            assertNotNull(it.atomicBadge)
            assertNull(it.atomicBadge!!.trustedSender)
        }
    }
}
