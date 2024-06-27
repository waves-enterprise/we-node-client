package com.wavesenterprise.sdk.node.client.feign.privacy

import com.wavesenterprise.sdk.node.client.http.privacy.ForceSyncDto
import com.wavesenterprise.sdk.node.client.http.privacy.PolicyItemInfoResponseDto
import com.wavesenterprise.sdk.node.client.http.privacy.SendDataRequestDto
import com.wavesenterprise.sdk.node.client.http.tx.PolicyDataHashTxDto
import feign.Headers
import feign.Param
import feign.RequestLine

interface WePrivacyServiceApiFeign {

    @Headers("Content-Type: application/json")
    @RequestLine("POST /privacy/sendData")
    fun forceSync(): ForceSyncDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /privacy/forceSync/{policyId}")
    fun forceSyncByPolicyId(policyId: String): ForceSyncDto

    @RequestLine("GET /privacy/{policyId}/getData/{policyItemHash}")
    fun getDataFromPrivacy(
        @Param("policyId") policyId: String,
        @Param("policyItemHash") policyItemHash: String,
    ): ByteArray

    @Headers("Content-Type: application/json")
    @RequestLine("GET /privacy/{policyId}/getLargeData/{policyItemHash}")
    fun getLargeDataFromPrivacy(
        @Param("policyId") policyId: String,
        @Param("policyItemHash") policyItemHash: String,
    ): ByteArray

    @Headers("Content-Type: application/json")
    @RequestLine("GET /privacy/{policyId}/recipients")
    fun getPolicyRecipients(
        @Param("policyId") policyId: String,
    ): List<String>

    @Headers("Content-Type: application/json")
    @RequestLine("GET /privacy/{policyId}/hashes")
    fun getPolicyHashes(
        @Param("policyId") policyId: String,
    ): List<String>

    @Headers("Content-Type: application/json")
    @RequestLine("GET /privacy/{policyId}/transactions")
    fun getPolicyDataHashTxs(
        @Param("policyId") policyId: String,
    ): List<String>

    @Headers("Content-Type: application/json")
    @RequestLine("GET /privacy/{policyId}/getInfo/{policyItemHash}")
    fun getPolicyItemInfo(
        @Param("policyId") policyId: String,
        @Param("policyItemHash") policyItemHash: String,
    ): PolicyItemInfoResponseDto

//    @Headers("Content-Type: application/json")
//    @RequestLine("GET /privacy/getInfos")
//    fun getPolicyItemInfos(): PolicyItemInfoResponseDto

    @Headers("Content-Type: application/json")
    @RequestLine("POST /privacy/sendData?broadcast={broadcast}")
    fun sendDataToPrivacy(
        request: SendDataRequestDto,
        @Param("broadcast") broadcast: Boolean,
    ): PolicyDataHashTxDto

//    @Headers("Content-Type: multipart/form-data")
//    @RequestLine("POST /privacy/sendData")
//    fun sendLargeData(
//        @Param("request") request: String,
//        @Param("data") data: File,
//    ): PolicyDataHashTxDto

    @Headers("Content-Type: application/json")
    @RequestLine("GET /privacy/{policyId}/owners")
    fun getPolicyOwners(
        @Param("policyId") policyId: String,
    ): List<String>
}
