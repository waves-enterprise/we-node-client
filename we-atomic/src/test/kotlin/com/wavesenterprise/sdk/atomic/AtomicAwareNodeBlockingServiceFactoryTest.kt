package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx
import com.wavesenterprise.sdk.node.domain.tx.Tx
import com.wavesenterprise.sdk.node.test.data.TestDataFactory
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AtomicAwareNodeBlockingServiceFactoryTest {

    lateinit var atomicAwareNodeBlockingServiceFactory: AtomicAwareNodeBlockingServiceFactory
    lateinit var atomicAwareContextManager: AtomicAwareContextManager

    private val nodeBlockingServiceFactory: NodeBlockingServiceFactory = mockk()
    private val txService: TxService = mockk()

    @BeforeEach
    fun setUp() {
        every { txService.broadcast(any()) } returns TestDataFactory.callContractTx()
        every { nodeBlockingServiceFactory.txService() } returns txService
        atomicAwareNodeBlockingServiceFactory = AtomicAwareNodeBlockingServiceFactory(nodeBlockingServiceFactory)
        atomicAwareContextManager = atomicAwareNodeBlockingServiceFactory.atomicAwareContextManager
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
}
