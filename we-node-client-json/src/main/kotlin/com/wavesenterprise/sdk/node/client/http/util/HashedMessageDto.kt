package com.wavesenterprise.sdk.node.client.http.util

import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.util.HashedMessage

data class HashedMessageDto(
    val message: String,
    val hash: String,
) {

    companion object {

        @JvmStatic
        fun HashedMessage.toDto() = HashedMessageDto(
            message = message,
            hash = hash.asBase58String(),
        )

        @JvmStatic
        fun HashedMessageDto.toDomain() = HashedMessage(
            message = message,
            hash = Hash.fromStringBase58(hash),
        )
    }
}
