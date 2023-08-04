package com.wavesenterprise.sdk.node.client.feign.util

import com.wavesenterprise.sdk.node.client.blocking.util.NodeUtilsService
import com.wavesenterprise.sdk.node.client.http.util.HashedMessageDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.util.TimeDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.util.HashedMessage
import com.wavesenterprise.sdk.node.domain.util.Time

class FeignNodeUtilsService(
    private val weUtilsServiceApiFeign: WeUtilsServiceApiFeign,
) : NodeUtilsService {

    override fun currentTime(): Time =
        weUtilsServiceApiFeign.currentTime().toDomain()

    override fun hashSecure(message: String): HashedMessage =
        weUtilsServiceApiFeign.hashSecure(message).toDomain()

    override fun fastHash(message: String): HashedMessage =
        weUtilsServiceApiFeign.fastHash(message).toDomain()
}
