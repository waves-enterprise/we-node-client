package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory

class DefaultNodeServiceFactoryWrapper(
    private val nodeBlockingServiceFactory: NodeBlockingServiceFactory,
    override val name: String,
    override val nodeOwnerAddress: Address,
    override val keyStorePassword: Password,
) : NodeServiceFactoryWrapper, NodeBlockingServiceFactory by nodeBlockingServiceFactory {

    private val addresses: List<Address> = nodeBlockingServiceFactory.addressService().getAddresses()
    private val nodeOwner: NodeOwner = nodeBlockingServiceFactory.nodeInfoService().getNodeOwner()

    override fun getAddresses(): List<Address> = addresses

    override fun getNodeOwner(): NodeOwner = nodeOwner

    override fun getPolicyRecipients(policyId: PolicyId): List<Address> =
        nodeBlockingServiceFactory.privacyService().recipients(policyId = policyId)
}
