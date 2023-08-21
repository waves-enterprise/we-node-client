package com.wavesenterprise.sdk.node.client.blocking.credentials

import com.wavesenterprise.sdk.node.domain.Address

interface SenderAddressProvider {
    fun address(): Address
}
