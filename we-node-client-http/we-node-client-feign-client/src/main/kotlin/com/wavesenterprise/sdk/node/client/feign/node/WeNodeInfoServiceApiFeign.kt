package com.wavesenterprise.sdk.node.client.feign.node

import com.wavesenterprise.sdk.node.domain.http.NodeOwnerDto
import feign.Headers
import feign.RequestLine

interface WeNodeInfoServiceApiFeign {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /node/config")
    fun getNodeConfig(): String // TODO: Add NodeConfigDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /node/owner")
    fun getNodeOwnerAddress(): NodeOwnerDto
}
