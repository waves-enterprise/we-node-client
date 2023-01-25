package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.sign.SignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx

interface NodesResolver {
    fun getOrderedAliveNodes(): List<NodeServiceFactoryWrapper>

    fun getOrderedAliveNodesForSign(sign: SignRequest<out Tx>): List<NodeServiceFactoryWrapper>

    fun getOrderedAliveNodesWithCredsForPrivacy(policyId: PolicyId): List<NodeServiceFactoryWrapper>

    fun getOrderedAliveNodesForPrivacyInfo(policyId: PolicyId): List<NodeServiceFactoryWrapper>

    fun getOrderedAliveNodesForPrivacyData(policyId: PolicyId, dataHash: Hash): List<NodeServiceFactoryWrapper>
}
