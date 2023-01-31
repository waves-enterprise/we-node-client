package com.wavesenterprise.sdk.node.client.blocking.credentials

import com.wavesenterprise.sdk.node.domain.Address

interface NodeCredentialsProvider {
    fun getPassword(address: Address): String
}
