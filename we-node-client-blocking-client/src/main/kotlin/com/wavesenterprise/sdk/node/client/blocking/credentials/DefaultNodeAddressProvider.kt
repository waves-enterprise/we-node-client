package com.wavesenterprise.sdk.node.client.blocking.credentials

import com.wavesenterprise.sdk.node.domain.Address

class DefaultNodeAddressProvider(
    private val address: Address,
) : NodeAddressProvider {
    override fun address(): Address = address
}
