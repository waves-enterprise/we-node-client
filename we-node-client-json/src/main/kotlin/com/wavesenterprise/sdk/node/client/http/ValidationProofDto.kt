package com.wavesenterprise.sdk.node.client.http

import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.ValidationProof

data class ValidationProofDto(
    val validatorPublicKey: String,
    val signature: String,
) {
    companion object {
        @JvmStatic
        fun ValidationProof.toDto(): ValidationProofDto =
            ValidationProofDto(
                validatorPublicKey = validatorPublicKey.asBase58String(),
                signature = signature.asBase58String(),
            )

        @JvmStatic
        fun ValidationProofDto.toDomain(): ValidationProof =
            ValidationProof(
                validatorPublicKey = PublicKey.fromBase58(validatorPublicKey),
                signature = Signature.fromBase58(signature),
            )
    }
}
