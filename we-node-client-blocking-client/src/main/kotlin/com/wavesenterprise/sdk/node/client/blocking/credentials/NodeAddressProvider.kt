package com.wavesenterprise.sdk.node.client.blocking.credentials

import com.wavesenterprise.sdk.node.domain.Address

interface NodeAddressProvider {
    fun address(): Address
}
