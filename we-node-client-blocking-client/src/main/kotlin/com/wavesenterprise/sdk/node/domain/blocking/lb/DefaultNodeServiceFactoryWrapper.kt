package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory

class DefaultNodeServiceFactoryWrapper(
    private val nodeBlockingServiceFactory: NodeBlockingServiceFactory,
    override val name: String,
    override val nodeCredentials: NodeCredentials,
) : NodeServiceFactoryWrapper, NodeBlockingServiceFactory by nodeBlockingServiceFactory {

    private val addresses: List<Address> = addressService().getAddresses()
    private val nodeOwner: NodeOwner = nodeInfoService().getNodeOwner()

    override fun getAddresses(): List<Address> = addresses

    override fun getNodeOwner(): NodeOwner = nodeOwner

    override fun getPolicyRecipients(policyId: PolicyId): List<Address> =
        privacyService().recipients(policyId = policyId)
}
