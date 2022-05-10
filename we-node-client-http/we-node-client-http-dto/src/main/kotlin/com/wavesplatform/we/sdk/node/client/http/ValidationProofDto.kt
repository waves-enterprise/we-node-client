package com.wavesplatform.we.sdk.node.client.http

import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.ValidationProof

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
