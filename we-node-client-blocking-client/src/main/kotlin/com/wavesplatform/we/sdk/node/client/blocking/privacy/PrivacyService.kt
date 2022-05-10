package com.wavesplatform.we.sdk.node.client.blocking.privacy

import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemDataResponse
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemInfoResponse
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemRequest
import com.wavesplatform.we.sdk.node.client.privacy.SendDataRequest
import com.wavesplatform.we.sdk.node.client.privacy.SendDataResponse

interface PrivacyService {
    fun sendData(request: SendDataRequest): SendDataResponse
    fun info(request: PolicyItemRequest): PolicyItemInfoResponse
    fun data(request: PolicyItemRequest): PolicyItemDataResponse
    fun exists(request: PolicyItemRequest): Boolean
}
