package com.wavesplatform.we.sdk.node.client.privacy.event

import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PolicyId

sealed interface PrivacyEvent {
    val policyId: PolicyId
    val dataHash: Hash

    data class DataAcquired(
        override val policyId: PolicyId,
        override val dataHash: Hash,
    ) : PrivacyEvent

    data class DataInvalidated(
        override val policyId: PolicyId,
        override val dataHash: Hash,
    ) : PrivacyEvent
}
