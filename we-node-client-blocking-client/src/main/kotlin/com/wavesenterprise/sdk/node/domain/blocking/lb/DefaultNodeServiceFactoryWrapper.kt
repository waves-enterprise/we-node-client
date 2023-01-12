package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.blocking.node.NodeBlockingServiceFactory

class DefaultNodeServiceFactoryWrapper(
    override val nodeBlockingServiceFactory: NodeBlockingServiceFactory,
    override val name: String,
    override val nodeCredentials: NodeCredentials,
) : NodeServiceFactoryWrapper, NodeBlockingServiceFactory by nodeBlockingServiceFactory {

    private val addressesCache: List<Address> by lazy { addressService().getAddresses() }
    private val nodeOwnerCache: NodeOwner by lazy { nodeInfoService().getNodeOwner() }

    override fun getAddresses(): List<Address> = addressesCache

    override fun getNodeOwner(): NodeOwner = nodeOwnerCache

    override fun getPolicyRecipients(policyId: PolicyId): List<Address> =
        privacyService().recipients(policyId = policyId)
}
