package com.wavesenterprise.sdk.node.domain.address

import com.wavesenterprise.sdk.node.domain.Password

data class SignMessageRequest(
    val message: Message,
    val password: Password,
)
