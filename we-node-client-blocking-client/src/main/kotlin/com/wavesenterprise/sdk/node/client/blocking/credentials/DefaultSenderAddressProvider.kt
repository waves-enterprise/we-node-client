package com.wavesenterprise.sdk.node.client.blocking.credentials

import com.wavesenterprise.sdk.node.domain.Address

class DefaultSenderAddressProvider(
    private val address: Address,
) : SenderAddressProvider {
    override fun address(): Address = address
}
