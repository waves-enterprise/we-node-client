package com.wavesenterprise.sdk.node.client.feign.blocks

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.blocking.blocks.BlocksService
import com.wavesenterprise.sdk.node.domain.blocks.BlockAtHeight
import com.wavesenterprise.sdk.node.domain.blocks.BlockHeaders
import com.wavesenterprise.sdk.node.domain.http.HeightDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.http.blocks.BlockAtHeightDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.http.blocks.BlockHeadersDto.Companion.toDomain

class FeignBlocksService(
    private val weBlocksServiceApiFeign: WeBlocksServiceApiFeign,
) : BlocksService {
    override fun blockHeight(): Height = weBlocksServiceApiFeign.getBlockHeight().toDomain()

    override fun blockSequence(fromHeight: Long, toHeight: Long): List<BlockAtHeight> =
        weBlocksServiceApiFeign.getBlocksSequence(
            fromHeight = fromHeight,
            toHeight = toHeight,
        ).map { it.toDomain() }

    override fun lastBlock(): BlockAtHeight = weBlocksServiceApiFeign.getLastBlock().toDomain()

    override fun childBlock(signature: Signature): BlockAtHeight =
        weBlocksServiceApiFeign.getChildBlock(
            signature = signature.asBase58String(),
        ).toDomain()

    override fun blockAtHeight(height: Long): BlockAtHeight =
        weBlocksServiceApiFeign.getBlockAtHeight(height).toDomain()

    override fun blockHeadersAtHeight(height: Long): BlockHeaders =
        weBlocksServiceApiFeign.getBlockHeadersAtHeight(height = height).toDomain()

    override fun blocksExtSequence(fromHeight: Long, toHeight: Long): List<BlockAtHeight> =
        weBlocksServiceApiFeign.getExtBlocksSequence(
            fromHeight = fromHeight,
            toHeight = toHeight,
        ).map { it.toDomain() }

    override fun blockHeadersSequence(fromHeight: Long, toHeight: Long): List<BlockHeaders> =
        weBlocksServiceApiFeign.getBlocksHeadersSequence(
            fromHeight = fromHeight,
            toHeight = toHeight,
        ).map { it.toDomain() }

    override fun lastBlockHeader(): BlockHeaders =
        weBlocksServiceApiFeign.getLastBlockHeaders().toDomain()

    override fun blockById(signature: Signature): BlockAtHeight =
        weBlocksServiceApiFeign.getBlockById(signature = signature.asBase58String()).toDomain()

    override fun blockHeightById(signature: Signature): Height =
        weBlocksServiceApiFeign.getBlockHeightById(signature = signature.asBase58String()).toDomain()

    override fun firstBlock(): BlockAtHeight = weBlocksServiceApiFeign.getFirstBlock().toDomain()

    override fun blocksByAddress(address: Address, fromHeight: Long, toHeight: Long): List<BlockAtHeight> =
        weBlocksServiceApiFeign.getBlocksSequenceByAddress(
            address = address.asBase58String(),
            fromHeight = fromHeight,
            toHeight = toHeight,
        ).map { it.toDomain() }
}
