package com.wavesenterprise.sdk.node.domain.blocking.credentials

import com.wavesenterprise.sdk.node.domain.Address

class DefaultNodeCredentialsProvider(
    val credentialsMap: Map<Address, String>,
) : NodeCredentialsProvider {
    override fun getPassword(address: Address) = requireNotNull(credentialsMap[address])
}
