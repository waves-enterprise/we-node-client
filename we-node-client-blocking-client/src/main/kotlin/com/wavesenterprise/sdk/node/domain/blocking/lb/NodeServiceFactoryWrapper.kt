package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PolicyId

interface NodeServiceFactoryWrapper {
    val name: String
    val nodeOwnerAddress: Address
    val keyStorePassword: Password

    fun getAddresses(): List<Address>

    fun getNodeOwner(): NodeOwner

    fun getPolicyRecipients(policyId: PolicyId): List<Address>
}
