package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx
import com.wavesenterprise.sdk.node.test.data.TestDataFactory
import com.wavesenterprise.sdk.tx.signer.TxSigner
import io.mockk.Called
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AtomicBroadcasterTest {

    private lateinit var atomicBroadcaster: AtomicBroadcaster

    private val txSigner: TxSigner = mockk()
    private val atomicAwareContextManager: AtomicAwareContextManager = mockk()
    private val atomicAwareNodeBlockingServiceFactory: NodeBlockingServiceFactory = mockk()
    private val txService: TxService = mockk()

    @BeforeEach
    fun init() {
        every { atomicAwareContextManager.beginAtomic() } just Runs
        every { atomicAwareContextManager.clearContext() } just Runs

        every { atomicAwareNodeBlockingServiceFactory.txService() } returns txService

        atomicBroadcaster = AtomicBroadcaster(
            txSigner = txSigner,
            atomicAwareContextManager = atomicAwareContextManager,
            atomicAwareNodeBlockingServiceFactory = atomicAwareNodeBlockingServiceFactory,
        )
    }

    @Test
    fun `should return result of block and don't broadcast empty atomic tx`() {
        every { atomicAwareContextManager.commitAtomic() } returns TestDataFactory.atomicSignRequest(txs = emptyList())

        val blockResult = atomicBroadcaster.doInAtomic { TestService().testBlock() }
        assertEquals(TEST_BLOCK_RESULT, blockResult)
        verify { txSigner.sign(any<AtomicSignRequest>()) wasNot Called }
        verify { txService.broadcast(any()) wasNot Called }
    }

    @Test
    fun `should return result of block and broadcast atomic tx`() {
        every { atomicAwareContextManager.commitAtomic() } returns TestDataFactory.atomicSignRequest(
            txs = listOf(TestDataFactory.createPolicyTx(), TestDataFactory.policyDataHashTx()),
        )
        every { txSigner.sign(any<AtomicSignRequest>()) } returns TestDataFactory.atomicTx()
        every { txService.broadcast(any<AtomicTx>()) } returns TestDataFactory.atomicTx()

        val blockResult = atomicBroadcaster.doInAtomic { TestService().testBlock() }
        assertEquals(TEST_BLOCK_RESULT, blockResult)
        verify { txSigner.sign(any<AtomicSignRequest>()) }
        verify { txService.broadcast(any()) }
    }

    class TestService {
        fun testBlock(): String {
            return TEST_BLOCK_RESULT
        }
    }

    companion object {
        const val TEST_BLOCK_RESULT = "test-block-result"
    }
}
