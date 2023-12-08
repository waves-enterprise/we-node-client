package com.wavesenterprise.sdk.node.client.feign.alias

import com.wavesenterprise.sdk.node.client.http.AddressDto
import feign.Param
import feign.RequestLine
import java.util.Optional

interface WeAliasServiceApiFeign {
    @RequestLine("GET /alias/by-address/{address}")
    fun getAliasesByAddress(@Param("address") address: String): List<String>

    @RequestLine("GET /alias/by-alias/{alias}")
    fun getAddressByAlias(@Param("alias") alias: String): Optional<AddressDto>
}
