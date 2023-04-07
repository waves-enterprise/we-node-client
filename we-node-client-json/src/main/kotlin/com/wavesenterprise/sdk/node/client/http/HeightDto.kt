package com.wavesenterprise.sdk.node.client.http

import com.wavesenterprise.sdk.node.domain.Height

data class HeightDto(
    val height: Long,
) {
    companion object {
        @JvmStatic
        fun HeightDto.toDomain() = Height(
            value = height,
        )
    }
}
