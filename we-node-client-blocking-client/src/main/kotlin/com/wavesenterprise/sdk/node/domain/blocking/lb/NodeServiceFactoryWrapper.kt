package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory

interface NodeServiceFactoryWrapper {
    val nodeBlockingServiceFactory: NodeBlockingServiceFactory
    val name: String
    val nodeCredentials: NodeCredentials

    fun getAddresses(): List<Address>

    fun getNodeOwner(): NodeOwner

    fun getPolicyRecipients(policyId: PolicyId): List<Address>
}
