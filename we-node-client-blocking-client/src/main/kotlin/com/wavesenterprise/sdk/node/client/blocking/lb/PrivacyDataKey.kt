package com.wavesenterprise.sdk.node.client.blocking.lb

import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId

data class PrivacyDataKey(
    val policyId: PolicyId,
    val dataHash: Hash,
)
