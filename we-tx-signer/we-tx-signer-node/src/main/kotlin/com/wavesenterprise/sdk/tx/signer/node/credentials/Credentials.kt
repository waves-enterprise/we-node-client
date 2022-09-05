package com.wavesenterprise.sdk.tx.signer.node.credentials

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Password

data class Credentials(
    val senderAddress: Address,
    val password: Password,
)
