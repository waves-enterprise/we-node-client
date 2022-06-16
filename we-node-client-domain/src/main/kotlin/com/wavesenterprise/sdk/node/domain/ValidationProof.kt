package com.wavesenterprise.sdk.node.domain

data class ValidationProof(
    val validatorPublicKey: PublicKey,
    val signature: Signature,
)
