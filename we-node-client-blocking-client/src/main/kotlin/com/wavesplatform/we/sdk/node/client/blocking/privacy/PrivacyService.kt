package com.wavesplatform.we.sdk.node.client.blocking.privacy

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.privacy.Data
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemInfoResponse
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemRequest
import com.wavesplatform.we.sdk.node.client.privacy.SendDataRequest
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx

interface PrivacyService {
    fun sendData(request: SendDataRequest): PolicyDataHashTx
    fun info(request: PolicyItemRequest): PolicyItemInfoResponse
    fun data(request: PolicyItemRequest): Data
    fun exists(request: PolicyItemRequest): Boolean
    fun recipients(policyId: PolicyId): List<Address>
    fun owners(policyId: PolicyId): List<Address>
    fun hashes(policyId: PolicyId): List<Address>
}
