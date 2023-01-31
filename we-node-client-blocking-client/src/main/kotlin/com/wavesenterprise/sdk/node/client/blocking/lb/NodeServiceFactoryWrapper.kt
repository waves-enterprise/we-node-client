package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.PolicyId

interface NodeServiceFactoryWrapper {
    val nodeBlockingServiceFactory: NodeBlockingServiceFactory
    val name: String

    fun getAddresses(): List<Address>

    fun getNodeOwner(): NodeOwner

    fun getPolicyRecipients(policyId: PolicyId): List<Address>
}
