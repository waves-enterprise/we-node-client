package com.wavesenterprise.sdk.node.client.http.address

import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureResponse

data class VerifyMessageSignatureResponseDto(
    val valid: Boolean,
) {
    companion object {
        @JvmStatic
        fun VerifyMessageSignatureResponse.toDto(): VerifyMessageSignatureResponseDto =
            VerifyMessageSignatureResponseDto(
                valid = valid,
            )

        @JvmStatic
        fun VerifyMessageSignatureResponseDto.toDomain(): VerifyMessageSignatureResponse =
            VerifyMessageSignatureResponse(
                valid = valid,
            )
    }
}
