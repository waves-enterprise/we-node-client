package com.wavesenterprise.sdk.tx.signer.node

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Password

interface SignCredentialsService {
    fun senderAddress(): Address
    fun password(): Password
}
