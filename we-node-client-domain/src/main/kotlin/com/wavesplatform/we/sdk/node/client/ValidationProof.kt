package com.wavesplatform.we.sdk.node.client

data class ValidationProof(
    val validatorPublicKey: PublicKey,
    val signature: Signature,
)
