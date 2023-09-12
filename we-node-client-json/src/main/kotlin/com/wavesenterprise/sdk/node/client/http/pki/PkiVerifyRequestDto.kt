package com.wavesenterprise.sdk.node.client.http.pki

import com.wavesenterprise.sdk.node.domain.pki.PkiVerifyRequest

class PkiVerifyRequestDto(
    val inputData: String,
    val signature: String,
    val sigType: Int,
    val extendedKeyUsageList: List<String>,
) {
    companion object {

        @JvmStatic
        fun PkiVerifyRequest.toDto() =
            PkiVerifyRequestDto(
                inputData = inputData,
                signature = signature,
                sigType = sigType,
                extendedKeyUsageList = extendedKeyUsageList,
            )
    }
}
