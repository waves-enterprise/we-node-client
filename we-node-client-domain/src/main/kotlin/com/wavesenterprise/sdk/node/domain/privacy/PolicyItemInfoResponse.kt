package com.wavesenterprise.sdk.node.domain.privacy

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId

data class PolicyItemInfoResponse(
    val senderAddress: Address,
    val policyId: PolicyId,
    val info: PolicyItemFileInfo,
    val dataHash: Hash,
)
