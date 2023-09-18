package com.wavesenterprise.sdk.node.client.feign.node

import com.wavesenterprise.sdk.node.client.http.node.NodeConfigDto
import com.wavesenterprise.sdk.node.client.http.node.NodeOwnerDto
import feign.Headers
import feign.RequestLine

interface WeNodeInfoServiceApiFeign {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /node/config")
    fun getNodeConfig(): NodeConfigDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /node/owner")
    fun getNodeOwnerAddress(): NodeOwnerDto
}
