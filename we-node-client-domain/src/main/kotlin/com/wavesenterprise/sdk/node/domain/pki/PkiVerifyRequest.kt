package com.wavesenterprise.sdk.node.domain.pki

data class PkiVerifyRequest(
    val inputData: String, // todo replace on domain models
    val signature: String, //
    val sigType: Int,
    val extendedKeyUsageList: List<String>,
)
