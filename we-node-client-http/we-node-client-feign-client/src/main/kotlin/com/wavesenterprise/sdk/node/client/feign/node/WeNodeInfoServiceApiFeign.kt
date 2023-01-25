package com.wavesenterprise.sdk.node.client.feign.node

import com.wavesenterprise.sdk.node.client.http.node.NodeOwnerDto
import com.wavesenterprise.sdk.node.domain.node.NodeConfig
import feign.Headers
import feign.RequestLine

interface WeNodeInfoServiceApiFeign {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /node/config")
    fun getNodeConfig(): NodeConfig // TODO: Add NodeConfigDto with mapping

    @Headers("Content-Type: application/json")
    @RequestLine("GET /node/owner")
    fun getNodeOwnerAddress(): NodeOwnerDto
}
