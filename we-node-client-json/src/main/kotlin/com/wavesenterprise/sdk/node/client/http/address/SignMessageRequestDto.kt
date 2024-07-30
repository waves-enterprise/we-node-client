package com.wavesenterprise.sdk.node.client.http.address

import com.wavesenterprise.sdk.node.domain.address.SignMessageRequest

data class SignMessageRequestDto(
    val message: String,
    val password: String,
) {
    companion object {
        @JvmStatic
        fun SignMessageRequest.toDto(): SignMessageRequestDto =
            SignMessageRequestDto(
                message = message.value,
                password = password.value,
            )
    }
}
