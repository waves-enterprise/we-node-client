package com.wavesenterprise.sdk.node.client.feign.contract

import com.wavesenterprise.sdk.node.domain.http.DataEntryDto
import feign.Param
import feign.RequestLine
import java.util.Optional

interface WeContractServiceApiFeign {

    @RequestLine("GET /contracts/{contractId}/{key}")
    fun contractKey(
        @Param("contractId") contractId: String,
        @Param("key") key: String,
    ): Optional<DataEntryDto>

    @RequestLine("GET /contracts/{contractId}?offset={offset}&matches={matches}&limit={limit}")
    fun contractKeys(
        @Param("contractId") contractId: String,
        @Param("offset") limit: Int? = null,
        @Param("matches") offset: Int? = null,
        @Param("limit") matches: String? = null,
    ): List<DataEntryDto>
}
