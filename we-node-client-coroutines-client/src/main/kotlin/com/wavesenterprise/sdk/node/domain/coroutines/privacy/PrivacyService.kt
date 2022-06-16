package com.wavesenterprise.sdk.node.domain.coroutines.privacy

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.privacy.Data
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemRequest
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import com.wavesenterprise.sdk.node.domain.tx.PolicyDataHashTx

interface PrivacyService {
    suspend fun sendData(request: SendDataRequest): PolicyDataHashTx
    suspend fun info(request: PolicyItemRequest): PolicyItemInfoResponse
    suspend fun data(request: PolicyItemRequest): Data
    suspend fun exists(request: PolicyItemRequest): Boolean
    suspend fun recipients(policyId: PolicyId): List<Address>
    suspend fun owners(policyId: PolicyId): List<Address>
    suspend fun hashes(policyId: PolicyId): List<Hash>
}
