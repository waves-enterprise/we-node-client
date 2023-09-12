package com.wavesenterprise.sdk.node.client.feign.pki

import com.wavesenterprise.sdk.node.client.blocking.pki.PkiService
import com.wavesenterprise.sdk.node.client.http.pki.PkiVerifyRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.pki.PkiVerifyResponseDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.pki.PkiVerifyRequest
import com.wavesenterprise.sdk.node.domain.pki.PkiVerifyResponse

class FeignPkiService(
    private val wePkiServiceApiFeign: WePkiServiceApiFeign,
) : PkiService {
    override fun verify(request: PkiVerifyRequest): PkiVerifyResponse =
        wePkiServiceApiFeign.verify(request.toDto()).toDomain()
}
