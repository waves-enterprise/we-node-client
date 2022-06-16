package com.wavesenterprise.sdk.node.domain.privacy.event

import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId

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
