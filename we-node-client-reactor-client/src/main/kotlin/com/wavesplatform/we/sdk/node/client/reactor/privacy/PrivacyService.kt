package com.wavesplatform.we.sdk.node.client.reactor.privacy

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.privacy.Data
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemInfoResponse
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemRequest
import com.wavesplatform.we.sdk.node.client.privacy.SendDataRequest
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx
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
