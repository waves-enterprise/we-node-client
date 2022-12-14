package com.wavesenterprise.sdk.node.client.feign.privacy

import com.wavesenterprise.sdk.node.domain.http.tx.PolicyDataHashTxDto
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import feign.Headers
import feign.Param
import feign.RequestLine

interface WePrivacyServiceApiFeign {
    @Headers("Content-Type: application/json")
    @RequestLine("POST /privacy/sendData")
    fun sendDataToPrivacy(request: SendDataRequest): PolicyDataHashTxDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /privacy/{policyId}/recipients")
    fun getPolicyRecipients(
        @Param("policyId") policyId: String
    ): List<String>

//    @Headers("Content-Type: application/json")
//    @RequestLine("POST /privacy/sendData?broadcast={broadcast}")
//    fun sendDataToPrivacy(
//        request: SendDataRequest,
//        @Param("broadcast") broadcast: Boolean
//    ): PolicyDataHashTxDto
//
//    @RequestLine("GET /privacy/{policyId}/getData/{policyItemHash}")
//    fun getDataFromPrivacy(
//        @Param("policyId") policyId: String,
//        @Param("policyItemHash") policyItemHash: String
//    ): ByteArray
//
//    @RequestLine("GET /privacy/{policyId}/getInfo/{policyItemHash}")
//    fun getInfoFromPrivacy(
//        @Param("policyId") policyId: String,
//        @Param("policyItemHash") policyItemHash: String
//    ): PrivacyInfoResponse
//
//    @RequestLine("GET /privacy/{policyId}/recipients")
//    fun getPolicyRecipients(
//        @Param("policyId") policyId: String
//    ): List<String>
//
//    @RequestLine("GET /privacy/{policyId}/owners")
//    fun getPolicyOwners(
//        @Param("policyId") policyId: String
//    ): List<String>
//
//    @RequestLine("GET /privacy/{policyId}/hashes")
//    fun getPolicyHashes(
//        @Param("policyId") policyId: String
//    ): List<String>
//
//    @RequestLine("GET /privacy/forceSync/{policyId}")
//    fun forceSync(
//        @Param("policyId") policyId: String
//    ): ForceSyncResponse
}
