package com.wavesenterprise.sdk.node.client.feign.blocks

import com.wavesenterprise.sdk.node.client.http.DataEntryDto
import com.wavesenterprise.sdk.node.client.http.HeightDto
import com.wavesenterprise.sdk.node.client.http.blocks.BlockAtHeightDto
import com.wavesenterprise.sdk.node.client.http.blocks.BlockHeadersDto
import com.wavesenterprise.sdk.node.client.http.blocks.PoaConsensusDto
import com.wavesenterprise.sdk.node.client.http.blocks.PosConsensusDto
import com.wavesenterprise.sdk.node.client.http.tx.CreateContractTxDto
import com.wavesenterprise.sdk.node.client.http.tx.TxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.BlockVersion
import com.wavesenterprise.sdk.node.domain.Feature
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.Signature
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.math.BigDecimal

class FeignBlocksServiceTest {
    lateinit var feignBlocksService: FeignBlocksService
    private val weBlocksServiceApiFeign: WeBlocksServiceApiFeign = mockk()

    @BeforeEach
    fun setUp() {
        feignBlocksService = FeignBlocksService(
            weBlocksServiceApiFeign = weBlocksServiceApiFeign,
        )
    }

    @Test
    fun `should correct map height dto to domain`() {
        val height = 11111L
        every { weBlocksServiceApiFeign.getBlockHeight() } returns HeightDto(height = height)
        assertDoesNotThrow {
            feignBlocksService.blockHeight()
        }.apply {
            assertEquals(height, this.value)
        }
    }

    @Test
    fun `should correct map block at height dto to domain`() {
        val blockAtHeightDto = BlockAtHeightDto(
            reference = "5qWJh9aQ2hkwnBWygGYmrBhzMe5inRZ2r6WhEXz3VJsiMtASWkvbsVeZGychZKzcPDbWmpzdhQwNQJ19PfK2dst9",
            blockSize = 589,
            features = listOf(1, 2, 3),
            signature = "4U4Hmg4mDYrvxaZ3JVzL1Z1piPDZ1PJ61vd1PeS7ESZFkHsUCUqeeAZoszTVr43Z4NV44dqbLv9WdrLytDL6gHuv",
            fee = BigDecimal.valueOf(5000000L),
            generator = "3NkZd8Xd4KsuPiNVsuphRNCZE3SqJycqv8d",
            transactionCount = 1L,
            transactions = listOf(
                CreateContractTxDto(
                    senderPublicKey =
                    "4L4XEpNpesX9r6rVJ8hW1TrMiNCZ6SMvRuWPKB7T47wKfnp4D84XBUv7xsa36CGwoyK3fzfojivwonHNrsX2fLBL",
                    image = "registry.weintegrator.com/icore-sc/we-contract-sdk/samples/my-demo-contract:1.2.0",
                    fee = 0,
                    id = "8mYpzj5rVVQSp2DgsyMvoMSViHtX94dJHkPyX2xo855y",
                    imageHash = "2fad12f4de059e29f92e7d31b91b6cf8388bc0cadb2fdfd194b25e21193bd7cc",
                    type = 103,
                    params = listOf(
                        DataEntryDto(
                            key = "action",
                            type = "string",
                            value = "createContract"
                        )
                    ),
                    version = 2,
                    sender = "3M3ybNZvLG7o7rnM4F7ViRPnDTfVggdfmRX",
                    feeAssetId = null,
                    proofs = listOf(
                        "378ShzisNBmGWEXdVbkWMqkfhhFuMYFJXSnG8bN5j7veUDXQUHzATEyxVuMwA1MCArb23kGrbA9iY213sK3WxL3d",
                    ),
                    contractName = "demo-contract",
                    timestamp = 1656952109871L,
                    height = 5577377L,
                    atomicBadge = null,
                    apiVersion = null,
                    validationPolicy = null,
                )
            ),
            version = 12,
            poaConsensus = PoaConsensusDto(
                overallSkippedRound = 2,
            ),
            posConsensus = PosConsensusDto(
                baseTarget = 249912231L,
                generationSignature = "LM83w6eWQHnLJF2D9RQNdNcHAdnZLCLWrn5bfcoqcZy",
            ),
            timestamp = 1568287320962L,
            height = 48260L,
        )
        val requestedHeight = 11111L
        every { weBlocksServiceApiFeign.getBlockAtHeight(requestedHeight) } returns blockAtHeightDto
        assertDoesNotThrow {
            feignBlocksService.blockAtHeight(height = requestedHeight)
        }.apply {
            assertEquals(blockAtHeightDto.reference, reference)
            assertEquals(blockAtHeightDto.blockSize, blockSize)
            assertEquals(blockAtHeightDto.features.map { Feature(it) }, features)
            assertEquals(Signature.fromBase58(blockAtHeightDto.signature), signature)
            assertEquals(Fee(blockAtHeightDto.fee.longValueExact()), fee)
            assertEquals(blockAtHeightDto.generator, generator)
            assertEquals(blockAtHeightDto.transactionCount, transactionCount)
            assertEquals(blockAtHeightDto.transactions.map { it.toDomain() }, transactions)
            assertEquals(BlockVersion(blockAtHeightDto.version), version)
            assertTrue(poaConsensus != null)
            poaConsensus!!.apply {
                assertEquals(blockAtHeightDto.poaConsensus!!.overallSkippedRound, overallSkippedRound)
            }
            assertTrue(posConsensus != null)
            posConsensus!!.apply {
                assertEquals(blockAtHeightDto.posConsensus!!.baseTarget, baseTarget)
                assertEquals(blockAtHeightDto.posConsensus!!.generationSignature, generationSignature)
            }
            assertEquals(blockAtHeightDto.timestamp, timestamp)
            assertEquals(Height.fromLong(blockAtHeightDto.height), height)
        }
    }

    @Test
    fun `should correct map block headers dto to domain`() {
        val requestedHeight = 11111L
        val blockHeadersDto = BlockHeadersDto(
            reference = "2MFnZY4hyi796xYFw3uKu7SrDowsffk5Lx7oRogqtUJ5qzyYc75x3BaN3xXuM2yvzjCsxAxyJVV1JbswLf9BVwNa",
            blockSize = 226,
            features = listOf(1, 2, 3),
            signature = "5E2XthFEC8msu6HsW7FsCeYSEK85A8s4M5cZTydKJYNTRqnXUSwh1Fxt7i1mhxuG5fUhvQnT5UBgg95Ayo4Ft5zo",
            generator = "3M3xGmJGmxBv2aZ4UFmn93rHxVXTJDKSAnh",
            transactionCount = 0,
            version = 3,
            poaConsensus = PoaConsensusDto(
                overallSkippedRound = 8184788,
            ),
            timestamp = 1626675751200L,
            height = 552123L,
        )
        every { weBlocksServiceApiFeign.getBlockHeadersAtHeight(requestedHeight) } returns blockHeadersDto
        assertDoesNotThrow {
            feignBlocksService.blockHeadersAtHeight(requestedHeight)
        }.apply {
            assertEquals(blockHeadersDto.reference, reference)
            assertEquals(blockHeadersDto.blockSize, blockSize)
            assertEquals(blockHeadersDto.features.map { Feature(it) }, features)
            assertEquals(Signature.fromBase58(blockHeadersDto.signature), signature)
            assertEquals(blockHeadersDto.generator, generator)
            assertEquals(blockHeadersDto.transactionCount, transactionCount)
            assertEquals(BlockVersion.fromInt(blockHeadersDto.version), version)
            assertTrue(posConsensus == null)
            assertTrue(poaConsensus != null)
            poaConsensus!!.apply {
                assertEquals(blockHeadersDto.poaConsensus!!.overallSkippedRound, overallSkippedRound)
            }
            assertEquals(blockHeadersDto.timestamp, timestamp)
            assertEquals(Height.fromLong(blockHeadersDto.height), height)
        }
    }
}
