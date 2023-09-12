package com.wavesenterprise.sdk.node.client.blocking.pki

import com.wavesenterprise.sdk.node.domain.pki.PkiVerifyRequest
import com.wavesenterprise.sdk.node.domain.pki.PkiVerifyResponse

interface PkiService {
    fun verify(request: PkiVerifyRequest): PkiVerifyResponse
}
