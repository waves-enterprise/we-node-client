package com.wavesplatform.we.sdk.node.client.coroutines.privacy

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.privacy.Data
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemInfoResponse
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemRequest
import com.wavesplatform.we.sdk.node.client.privacy.SendDataRequest
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx

interface PrivacyService {
    suspend fun sendData(request: SendDataRequest): PolicyDataHashTx
    suspend fun info(request: PolicyItemRequest): PolicyItemInfoResponse
    suspend fun data(request: PolicyItemRequest): Data
    suspend fun exists(request: PolicyItemRequest): Boolean
    suspend fun recipients(policyId: PolicyId): List<Address>
    suspend fun owners(policyId: PolicyId): List<Address>
    suspend fun hashes(policyId: PolicyId): List<Hash>
}
