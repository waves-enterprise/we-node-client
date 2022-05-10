package com.wavesplatform.we.sdk.node.client.privacy

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PolicyId

data class PolicyItemInfoResponse(
    val senderAddress: Address,
    val policyId: PolicyId,
    val info: PolicyItemFileInfo,
    val dataHash: Hash,
)
