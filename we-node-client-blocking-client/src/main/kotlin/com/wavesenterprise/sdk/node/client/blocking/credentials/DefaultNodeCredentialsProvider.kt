package com.wavesenterprise.sdk.node.client.blocking.credentials

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Password

class DefaultNodeCredentialsProvider(
    private val credentialsMap: Map<Address, Password>,
) : NodeCredentialsProvider {
    override fun getPassword(address: Address): Password = requireNotNull(credentialsMap[address])
}
