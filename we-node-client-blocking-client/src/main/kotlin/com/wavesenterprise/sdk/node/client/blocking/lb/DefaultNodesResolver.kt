package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.client.blocking.cache.LoadingCache
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx

class DefaultNodesResolver(
    private val nodeServiceFactoryWrappers: List<NodeServiceFactoryWrapper>,
    private val circuitBreaker: CircuitBreaker,
    private val recipientsCache: LoadingCache<PolicyId, Set<Address>>,
    private val privacyDataNodesCache: PrivacyDataNodesCache,
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
    ): List<NodeServiceFactoryWrapper> =
        getOrderedAliveNodesForPrivacy(policyId).sortedByDescending {
            privacyDataNodesCache.get(policyId, dataHash).contains(it.name)
        }

    private fun getOrderedAliveNodesForPrivacy(policyId: PolicyId): List<NodeServiceFactoryWrapper> =
        getOrderedAliveNodes().filter {
            nodeInPolicy(it, policyId)
        }

    private fun nodeInPolicy(client: NodeServiceFactoryWrapper, policyId: PolicyId) =
        client.runCatching {
            val policyRecipients = recipientsCache.loadNotNull(policyId) { getPolicyRecipients(policyId).toSet() }
            getNodeOwner().address in policyRecipients
        }.getOrDefault(false)

    private val orderedClientsWithProperties
        get() = nodeServiceFactoryWrappers.filter {
            circuitBreaker.isClosed(it.name)
        }.shuffled()
}
