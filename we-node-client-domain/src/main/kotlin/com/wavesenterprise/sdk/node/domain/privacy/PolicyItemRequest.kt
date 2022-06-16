package com.wavesenterprise.sdk.node.domain.privacy

import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId

data class PolicyItemRequest(
    val policyId: PolicyId,
    val dataHash: Hash,
)
