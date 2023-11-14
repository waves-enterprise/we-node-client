package com.wavesenterprise.sdk.node.domain.address

import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature

data class VerifyMessageSignatureRequest(
    val message: Message,
    val publicKey: PublicKey,
    val signature: Signature,
)
