package com.wavesplatform.we.sdk.node.client.coroutines.privacy

import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemDataResponse
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemInfoResponse
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemRequest
import com.wavesplatform.we.sdk.node.client.privacy.SendDataRequest
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx

interface PrivacyService {
    suspend fun sendData(request: SendDataRequest): PolicyDataHashTx
    suspend fun info(request: PolicyItemRequest): PolicyItemInfoResponse
    suspend fun data(request: PolicyItemRequest): PolicyItemDataResponse
    suspend fun exists(request: PolicyItemRequest): Boolean
}
