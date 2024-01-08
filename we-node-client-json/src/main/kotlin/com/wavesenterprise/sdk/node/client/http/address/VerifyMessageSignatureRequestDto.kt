package com.wavesenterprise.sdk.node.client.http.address

import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureRequest

data class VerifyMessageSignatureRequestDto(
    val message: String,
    val publicKey: String,
    val signature: String
) {
    companion object {
        @JvmStatic
        fun VerifyMessageSignatureRequest.toDto(): VerifyMessageSignatureRequestDto =
            VerifyMessageSignatureRequestDto(
                message = message.value,
                publicKey = publicKey.asBase58String(),
                signature = signature.asBase58String(),
            )
    }
}
