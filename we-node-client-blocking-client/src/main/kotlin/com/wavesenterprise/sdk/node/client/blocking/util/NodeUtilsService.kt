package com.wavesenterprise.sdk.node.client.blocking.util

import com.wavesenterprise.sdk.node.domain.util.HashedMessage
import com.wavesenterprise.sdk.node.domain.util.Time

interface NodeUtilsService {
    fun currentTime(): Time
    fun hashSecure(message: String): HashedMessage
    fun fastHash(message: String): HashedMessage
}
