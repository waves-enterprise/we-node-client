package com.wavesplatform.we.sdk.node.client.privacy

interface PrivacyService {
    suspend fun sendData(request: SendDataRequest): SendDataResponse
    suspend fun info(request: PolicyItemRequest): PolicyItemInfoResponse
    suspend fun data(request: PolicyItemRequest): PolicyItemDataResponse
    suspend fun exists(request: PolicyItemRequest): Boolean
}
