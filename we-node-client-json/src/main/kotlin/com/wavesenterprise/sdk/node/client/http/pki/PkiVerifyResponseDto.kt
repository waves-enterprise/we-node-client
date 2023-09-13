package com.wavesenterprise.sdk.node.client.http.pki

import com.wavesenterprise.sdk.node.domain.pki.PkiVerifyResponse

data class PkiVerifyResponseDto(
    val sigStatus: Boolean,
) {
    companion object {
        @JvmStatic
        fun PkiVerifyResponse.toDto() =
            PkiVerifyResponseDto(
                sigStatus = sigStatus,
            )

        @JvmStatic
        fun PkiVerifyResponseDto.toDomain() =
            PkiVerifyResponse(
                sigStatus = sigStatus,
            )
    }
}
