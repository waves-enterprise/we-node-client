package com.wavesenterprise.sdk.node.client.feign.util

import com.wavesenterprise.sdk.node.client.http.util.HashedMessageDto
import com.wavesenterprise.sdk.node.client.http.util.TimeDto
import feign.RequestLine

interface WeUtilsServiceApiFeign {

    @RequestLine("GET /utils/time")
    fun currentTime(): TimeDto

    @RequestLine("POST /utils/hash/secure")
    fun hashSecure(message: String): HashedMessageDto

    @RequestLine("POST /utils/fast/hash")
    fun fastHash(message: String): HashedMessageDto
}
