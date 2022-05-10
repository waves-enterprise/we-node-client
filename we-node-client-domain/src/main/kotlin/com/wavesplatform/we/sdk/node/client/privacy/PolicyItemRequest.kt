package com.wavesplatform.we.sdk.node.client.privacy

import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PolicyId

data class PolicyItemRequest(
    val policyId: PolicyId,
    val dataHash: Hash,
)
