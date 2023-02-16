package com.wavesenterprise.sdk.node.client.blocking.credentials

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Password

interface NodeCredentialsProvider {
    fun getPassword(address: Address): Password
}
