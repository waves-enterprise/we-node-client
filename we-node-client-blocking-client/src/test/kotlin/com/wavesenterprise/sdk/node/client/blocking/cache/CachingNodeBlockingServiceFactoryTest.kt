package com.wavesenterprise.sdk.node.client.blocking.cache

import com.wavesenterprise.sdk.node.client.blocking.atomicTx
import com.wavesenterprise.sdk.node.client.blocking.blockAtHeight
import com.wavesenterprise.sdk.node.client.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.client.blocking.callContractTx
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.policyItemInfoResponse
import com.wavesenterprise.sdk.node.client.blocking.policyItemRequest
import com.wavesenterprise.sdk.node.client.blocking.privacy.PrivacyService
import com.wavesenterprise.sdk.node.client.blocking.tx.TxService
import com.wavesenterprise.sdk.node.client.blocking.txInfo
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.TxId
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Optional

class CachingNodeBlockingServiceFactoryTest {
    private lateinit var cachingNodeBlockingServiceFactory: CachingNodeBlockingServiceFactory

    private val mockkNodeBlockingServiceFactory: NodeBlockingServiceFactory = mockk()
    private val mockkTxService: TxService = mockk()
    private val mockkPrivacyService: PrivacyService = mockk()
    private val mockkBlocksService: BlocksService = mockk()
    private val cachingNodeBlockingServiceFactoryBuilder: CachingNodeBlockingServiceFactoryBuilder =
        CachingNodeBlockingServiceFactoryBuilder.builder()

    @BeforeEach
    fun init() {
        every { mockkNodeBlockingServiceFactory.txService() } returns mockkTxService
        every { mockkNodeBlockingServiceFactory.privacyService() } returns mockkPrivacyService
        every { mockkNodeBlockingServiceFactory.blocksService() } returns mockkBlocksService

        cachingNodeBlockingServiceFactory = cachingNodeBlockingServiceFactoryBuilder.build(
            nodeBlockingServiceFactory = mockkNodeBlockingServiceFactory,
        )
    }

    @Test
    fun `should use txInfo cache`() {
        val txId = TxId("".toByteArray())
        val txInfo = txInfo()
        every { mockkTxService.txInfo(txId) } returns Optional.of(txInfo)

        repeat(2) { cachingNodeBlockingServiceFactory.txService().txInfo(txId) }

        verify(exactly = 1) { mockkTxService.txInfo(txId) }
    }

    @Test
    fun `shouldn't cache txInfo when tx service return empty Optional`() {
        val txId = TxId("txId".toByteArray())
        every { mockkTxService.txInfo(txId) } returns Optional.empty()

        repeat(2) { cachingNodeBlockingServiceFactory.txService().txInfo(txId) }

        verify(exactly = 2) { mockkTxService.txInfo(txId) }
    }

    @Test
    fun `should cache transaction info from block`() {
        val txsInBlock = listOf(callContractTx(), callContractTx())
        val blockAtHeight = blockAtHeight(txs = txsInBlock)
        val height = 11111L
        every { mockkBlocksService.blockAtHeight(height) } returns blockAtHeight

        cachingNodeBlockingServiceFactory.blocksService().blockAtHeight(height)
        txsInBlock.forEach {
            cachingNodeBlockingServiceFactory.txService().txInfo(it.id)
        }

        verify(exactly = 1) { mockkBlocksService.blockAtHeight(height) }
        verify(exactly = 0) { mockkTxService.txInfo(any()) }
    }

    @Test
    fun `should cache transaction info from blocks sequence`() {
        val txsInBlock = listOf(callContractTx(), callContractTx())
        val blocks = listOf(blockAtHeight(txs = txsInBlock))
        val fromHeight = 1L
        val toHeight = 2L

        every { mockkBlocksService.blockSequence(fromHeight, toHeight) } returns blocks

        cachingNodeBlockingServiceFactory.blocksService().blockSequence(fromHeight, toHeight)
        txsInBlock.forEach {
            cachingNodeBlockingServiceFactory.txService().txInfo(it.id)
        }

        verify(exactly = 1) { mockkBlocksService.blockSequence(fromHeight, toHeight) }
        verify(exactly = 0) { mockkTxService.txInfo(any()) }
    }

    @Test
    fun `should cache atomic transaction info with inner transactions from blocks sequence`() {
        val senderAddress = Address("address".toByteArray())
        val txInBlock = atomicTx(
            innerTxs = listOf(callContractTx(senderAddress = senderAddress)),
            senderAddress = senderAddress,
        )
        val blocks = listOf(blockAtHeight(txs = listOf(txInBlock)))
        val fromHeight = 1L
        val toHeight = 2L

        every { mockkBlocksService.blockSequence(fromHeight, toHeight) } returns blocks

        cachingNodeBlockingServiceFactory.blocksService().blockSequence(fromHeight, toHeight)
        txInBlock.let { atomicTx ->
            cachingNodeBlockingServiceFactory.txService().txInfo(atomicTx.id)
            atomicTx.txs.forEach { atomicInnerTx ->
                cachingNodeBlockingServiceFactory.txService().txInfo(atomicInnerTx.id)
            }
        }

        verify(exactly = 1) { mockkBlocksService.blockSequence(fromHeight, toHeight) }
        verify(exactly = 0) { mockkTxService.txInfo(any()) }
    }

    @Test
    fun `should cache policy item info response`() {
        val policyItemRequest = policyItemRequest()
        val policyItemInfoResponse = policyItemInfoResponse()
        every { mockkPrivacyService.info(policyItemRequest) } returns Optional.of(policyItemInfoResponse)

        repeat(2) { cachingNodeBlockingServiceFactory.privacyService().info(policyItemRequest) }

        verify(exactly = 1) { mockkPrivacyService.info(policyItemRequest) }
    }
}
