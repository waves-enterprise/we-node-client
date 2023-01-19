package com.wavesenterprise.sdk.node.client.feign.blocks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.wavesenterprise.sdk.node.client.feign.FeignNodeClientParams
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorDecoder
import com.wavesenterprise.sdk.node.client.feign.FeignNodeErrorMapper
import com.wavesenterprise.sdk.node.client.feign.FeignWeApiFactory
import com.wavesenterprise.sdk.node.domain.http.DataEntryDto
import com.wavesenterprise.sdk.node.domain.http.HeightDto
import com.wavesenterprise.sdk.node.domain.http.blocks.BlockAtHeightDto
import com.wavesenterprise.sdk.node.domain.http.blocks.BlockHeadersDto
import com.wavesenterprise.sdk.node.domain.http.blocks.PoaConsensusDto
import com.wavesenterprise.sdk.node.domain.http.blocks.PosConsensusDto
import com.wavesenterprise.sdk.node.domain.http.tx.CreateContractTxDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal

@WireMockTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeBlocksServiceApiFeignTest {

    lateinit var weBlocksApi: WeBlocksServiceApiFeign

    @BeforeAll
    fun init(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        weBlocksApi = FeignWeApiFactory.createClient(
            WeBlocksServiceApiFeign::class.java,
            FeignNodeClientParams(url = wireMockRuntimeInfo.httpBaseUrl),
            FeignNodeErrorDecoder(FeignNodeErrorMapper(jacksonObjectMapper()))
        )
    }

    @Test
    fun `should get current blockchain height`() {
        val blockHeight = weBlocksApi.getBlockHeight()
        assertEquals(heightDto, blockHeight)
    }

    @Test
    fun `should get block at specified height`() {
        val blockAtHeight = weBlocksApi.getBlockAtHeight(height = 11111L)
        assertEquals(blockAtHeightDto, blockAtHeight)
    }

    @Test
    fun `should get blocks sequence at range`() {
        val blocksAtHeight = weBlocksApi.getBlocksSequence(
            fromHeight = 1L,
            toHeight = 2L,
        )
        assertEquals(2, blocksAtHeight.size)
        blocksAtHeight.forEach { block ->
            assertEquals(blockAtHeightDto, block)
        }
    }

    @Test
    fun `should get last block`() {
        val lastBlock = weBlocksApi.getLastBlock()
        assertEquals(blockAtHeightDto, lastBlock)
    }

    @Test
    fun `should get block headers at height`() {
        val blockHeaders = weBlocksApi.getBlockHeadersAtHeight(1L)
        assertEquals(blockHeadersDto, blockHeaders)
    }

    @Test
    fun `should get last block headers`() {
        val blockHeaders = weBlocksApi.getLastBlockHeaders()
        assertEquals(blockHeadersDto, blockHeaders)
    }

    @Test
    fun `should get block headers sequence`() {
        val blockHeaders = weBlocksApi.getBlocksHeadersSequence(
            fromHeight = 1L,
            toHeight = 2L,
        )
        assertEquals(2, blockHeaders.size)
        blockHeaders.forEach { headers ->
            assertEquals(blockHeadersDto, headers)
        }
    }

    @Test
    fun `should get blocks by address at range`() {
        val blocksAtHeight = weBlocksApi.getBlocksSequenceByAddress(
            address = "3NkZd8Xd4KsuPiNVsuphRNCZE3SqJycqv8d",
            fromHeight = 1L,
            toHeight = 2L,
        )
        assertEquals(2, blocksAtHeight.size)
        blocksAtHeight.forEach { block ->
            assertEquals(blockAtHeightDto, block)
        }
    }

    @Test
    fun `should get first block at height`() {
        val firstBlock = weBlocksApi.getFirstBlock()
        assertEquals(blockAtHeightDto, firstBlock)
    }

    @Test
    fun `should get child block from a signature block`() {
        val childBlock = weBlocksApi.getChildBlock(
            signature = "4U4Hmg4mDYrvxaZ3JVzL1Z1piPDZ1PJ61vd1PeS7ESZFkHsUCUqeeAZoszTVr43Z4NV44dqbLv9WdrLytDL6gHuv",
        )
        assertEquals(blockAtHeightDto, childBlock)
    }

    @Test
    fun `should get blocks with extended transaction info`() {
        val extendedBlocks = weBlocksApi.getExtBlocksSequence(
            fromHeight = 1L,
            toHeight = 2L,
        )
        assertEquals(2, extendedBlocks.size)
        extendedBlocks.forEach { block ->
            assertEquals(blockAtHeightDto, block)
        }
    }

    @Test
    fun `should get block by id (signature)`() {
        val blockAtHeight = weBlocksApi.getBlockById(
            signature = "4U4Hmg4mDYrvxaZ3JVzL1Z1piPDZ1PJ61vd1PeS7ESZFkHsUCUqeeAZoszTVr43Z4NV44dqbLv9WdrLytDL6gHuv",
        )
        assertEquals(blockAtHeightDto, blockAtHeight)
    }

    @Test
    fun `should get block headers by id (signature)`() {
        val blockHeaders = weBlocksApi.getBlockHeightById(
            signature = "5E2XthFEC8msu6HsW7FsCeYSEK85A8s4M5cZTydKJYNTRqnXUSwh1Fxt7i1mhxuG5fUhvQnT5UBgg95Ayo4Ft5zo",
        )
        assertEquals(heightDto, blockHeaders)
    }

    companion object {
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
                        "378ShzisNBmGWEXdVbkWMqkfhhFuMYFJXSnG8bN5j7veUDXQUHzATEyxVuMwA1MCArb23kGrbA9iY213sK3WxL3d"
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
        val heightDto = HeightDto(
            height = 11111L,
        )
    }
}
