package com.wavesenterprise.sdk.node.client.blocking.privacy

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import java.util.Optional

interface PrivacyService {
    fun sendData(request: SendDataRequest): PolicyDataHashTx
    fun info(request: PolicyItemRequest): Optional<PolicyItemInfoResponse>
    fun data(request: PolicyItemRequest): Optional<Data>
    fun exists(request: PolicyItemRequest): Boolean
    fun recipients(policyId: PolicyId): List<Address>
    fun owners(policyId: PolicyId): List<Address>
    fun hashes(policyId: PolicyId): List<Hash>
}
