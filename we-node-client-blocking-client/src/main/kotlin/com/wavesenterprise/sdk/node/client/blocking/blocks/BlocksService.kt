package com.wavesenterprise.sdk.node.client.blocking.blocks

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.blocks.BlockAtHeight
import com.wavesenterprise.sdk.node.domain.blocks.BlockHeaders

interface BlocksService {

    fun blockHeight(): Height
    fun blockSequence(fromHeight: Long, toHeight: Long): List<BlockAtHeight>
    fun lastBlock(): BlockAtHeight
    fun childBlock(signature: Signature): BlockAtHeight
    fun blockAtHeight(height: Long): BlockAtHeight
    fun blockHeightById(signature: Signature): Height
    fun blockHeadersAtHeight(height: Long): BlockHeaders
    fun blocksExtSequence(fromHeight: Long, toHeight: Long): List<BlockAtHeight>
    fun blockHeadersSequence(fromHeight: Long, toHeight: Long): List<BlockHeaders>
    fun lastBlockHeader(): BlockHeaders
    fun blockById(signature: Signature): BlockAtHeight
    fun firstBlock(): BlockAtHeight
    fun blocksByAddress(address: Address, fromHeight: Long, toHeight: Long): List<BlockAtHeight>
}
