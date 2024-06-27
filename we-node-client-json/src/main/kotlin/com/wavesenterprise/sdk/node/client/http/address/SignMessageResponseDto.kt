package com.wavesenterprise.sdk.node.client.http.address

import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.address.Message
import com.wavesenterprise.sdk.node.domain.address.SignMessageResponse

data class SignMessageResponseDto(
    val message: String,
    val publicKey: String,
    val signature: String,
) {
    companion object {
        @JvmStatic
        fun SignMessageResponse.toDto(): SignMessageResponseDto =
            SignMessageResponseDto(
                message = message.value,
                publicKey = publicKey.asBase58String(),
                signature = signature.asBase58String(),
            )

        @JvmStatic
        fun SignMessageResponseDto.toDomain(): SignMessageResponse =
            SignMessageResponse(
                message = Message(value = message),
                publicKey = PublicKey.fromBase58(publicKey),
                signature = Signature.fromBase58(signature),
            )
    }
}
