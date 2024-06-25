package com.wavesenterprise.sdk.node.client.feign.contract

import com.wavesenterprise.sdk.node.client.http.DataEntryDto
import com.wavesenterprise.sdk.node.client.http.contract.ContractInfoDto
import com.wavesenterprise.sdk.node.client.http.contract.ContractTxStatusDto
import feign.Param
import feign.RequestLine
import java.util.Optional

interface WeContractServiceApiFeign {

    @RequestLine("GET /contracts/{contractId}/{key}")
    fun contractKey(
        @Param("contractId") contractId: String,
        @Param("key") key: String,
    ): Optional<DataEntryDto>

    @RequestLine("GET /contracts/{contractId}?limit={limit}&offset={offset}&matches={matches}")
    fun contractKeys(
        @Param("contractId") contractId: String,
        @Param("limit") limit: Int? = null,
        @Param("offset") offset: Int? = null,
        @Param("matches") matches: String? = null,
    ): List<DataEntryDto>

    @RequestLine("GET /contracts/info/{contractId}")
    fun contractInfo(
        @Param("contractId") contractId: String,
    ): ContractInfoDto

    @RequestLine("GET /contracts/status/{txId}")
    fun contractTxStatus(
        @Param("txId") txId: String,
    ): List<ContractTxStatusDto>
}
