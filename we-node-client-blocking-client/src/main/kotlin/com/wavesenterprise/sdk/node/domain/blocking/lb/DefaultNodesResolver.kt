package com.wavesenterprise.sdk.node.domain.blocking.lb

import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx

class DefaultNodesResolver(
    private val nodeServiceFactoryWrappers: List<NodeServiceFactoryWrapper>,
) : NodesResolver {

    override fun getOrderedAliveNodes(): List<NodeServiceFactoryWrapper> = orderedClientsWithProperties

    override fun getOrderedAliveNodesForSign(sign: SignRequest<out Tx>): List<NodeServiceFactoryWrapper> =
        getOrderedAliveNodes().filter {
            it.runCatching {
                getAddresses()
            }.getOrNull()?.contains(sign.senderAddress) ?: false
        }

    override fun getOrderedAliveNodesWithCredsForPrivacy(policyId: PolicyId): List<NodeServiceFactoryWrapper> =
        orderedClientsWithProperties
            .filter { client ->
                nodeInPolicy(client, policyId)
            }

    override fun getOrderedAliveNodesForPrivacyInfo(policyId: PolicyId): List<NodeServiceFactoryWrapper> =
        getOrderedAliveNodesForPrivacy(policyId)

    override fun getOrderedAliveNodesForPrivacyData(
        policyId: PolicyId,
        dataHash: Hash,
    ): List<NodeServiceFactoryWrapper> = getOrderedAliveNodesForPrivacy(policyId)
    // TODO: Add PrivacyDataHashesCache

    private fun getOrderedAliveNodesForPrivacy(policyId: PolicyId): List<NodeServiceFactoryWrapper> =
        getOrderedAliveNodes().filter {
            nodeInPolicy(it, policyId)
        }

    private fun nodeInPolicy(client: NodeServiceFactoryWrapper, policyId: PolicyId) =
        client.runCatching {
            val policyRecipients = getPolicyRecipients(policyId)
            getNodeOwner().address in policyRecipients
        }.getOrDefault(false)

    private val orderedClientsWithProperties
        get() = nodeServiceFactoryWrappers.filter { it.isAlive() }.shuffled()
}
