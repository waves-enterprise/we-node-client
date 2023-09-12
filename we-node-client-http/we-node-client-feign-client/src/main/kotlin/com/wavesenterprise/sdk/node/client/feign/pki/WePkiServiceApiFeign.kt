package com.wavesenterprise.sdk.node.client.feign.pki

import com.wavesenterprise.sdk.node.client.http.pki.PkiVerifyRequestDto
import com.wavesenterprise.sdk.node.client.http.pki.PkiVerifyResponseDto
import feign.Headers
import feign.RequestLine

interface WePkiServiceApiFeign {
    @Headers("Content-Type: application/json")
    @RequestLine("POST /pki/verify")
    fun verify(request: PkiVerifyRequestDto): PkiVerifyResponseDto
}
