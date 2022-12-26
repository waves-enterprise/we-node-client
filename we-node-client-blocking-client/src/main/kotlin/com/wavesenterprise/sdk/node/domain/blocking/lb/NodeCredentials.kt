package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Password

data class NodeCredentials(
    val address: Address,
    val keyStorePassword: Password,
)
