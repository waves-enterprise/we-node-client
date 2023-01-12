package com.wavesenterprise.sdk.node.domain.blocking.credentials

import com.wavesenterprise.sdk.node.domain.Address

interface NodeCredentialsProvider {
    fun getPassword(address: Address): String
}
