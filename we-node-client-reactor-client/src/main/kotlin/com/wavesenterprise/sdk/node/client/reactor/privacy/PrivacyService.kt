package com.wavesenterprise.sdk.node.client.reactor.privacy

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx
import reactor.core.publisher.Mono

interface PrivacyService {
    fun sendData(request: SendDataRequest): Mono<PolicyDataHashTx>
    fun info(request: PolicyItemRequest): Mono<PolicyItemInfoResponse>
    fun data(request: PolicyItemRequest): Mono<Data>
    fun exists(request: PolicyItemRequest): Mono<Boolean>
    fun recipients(policyId: PolicyId): Mono<List<Address>>
    fun owners(policyId: PolicyId): Mono<List<Address>>
    fun hashes(policyId: PolicyId): Mono<List<Hash>>
}
