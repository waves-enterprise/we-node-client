package com.wavesenterprise.sdk.node.client.feign.blocks

import com.wavesenterprise.sdk.node.client.http.HeightDto
import com.wavesenterprise.sdk.node.client.http.blocks.BlockAtHeightDto
import com.wavesenterprise.sdk.node.client.http.blocks.BlockHeadersDto
import feign.Headers
import feign.Param
import feign.RequestLine

@Suppress("TooManyFunctions")
interface WeBlocksServiceApiFeign {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/height")
    fun getBlockHeight(): HeightDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/at/{height}")
    fun getBlockAtHeight(@Param("height") height: Long): BlockAtHeightDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/seq/{from}/{to}")
    fun getBlocksSequence(
        @Param("from") fromHeight: Long,
        @Param("to") toHeight: Long,
    ): List<BlockAtHeightDto>

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/last")
    fun getLastBlock(): BlockAtHeightDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/first")
    fun getFirstBlock(): BlockAtHeightDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/headers/seq/{from}/{to}")
    fun getBlocksHeadersSequence(
        @Param("from") fromHeight: Long,
        @Param("to") toHeight: Long,
    ): List<BlockHeadersDto>

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/address/{address}/{from}/{to}")
    fun getBlocksSequenceByAddress(
        @Param("address") address: String,
        @Param("from") fromHeight: Long,
        @Param("to") toHeight: Long,
    ): List<BlockAtHeightDto>

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/child/{signature}")
    fun getChildBlock(@Param("signature") signature: String): BlockAtHeightDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/signature/{signature}")
    fun getBlockById(@Param("signature") signature: String): BlockAtHeightDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/headers/last")
    fun getLastBlockHeaders(): BlockHeadersDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/headers/at/{height}")
    fun getBlockHeadersAtHeight(@Param("height") height: Long): BlockHeadersDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/seqext/{from}/{to}")
    fun getExtBlocksSequence(
        @Param("from") fromHeight: Long,
        @Param("to") toHeight: Long,
    ): List<BlockAtHeightDto>

    @Headers("Content-Type: application/json")
    @RequestLine("GET /blocks/height/{signature}")
    fun getBlockHeightById(@Param("signature") signature: String): HeightDto
}
