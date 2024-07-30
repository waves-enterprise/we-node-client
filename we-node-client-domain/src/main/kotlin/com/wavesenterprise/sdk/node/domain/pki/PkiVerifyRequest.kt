package com.wavesenterprise.sdk.node.domain.pki

data class PkiVerifyRequest(
    // todo replace on domain models
    val inputData: String,
    val signature: String,
    val sigType: Int,
    val extendedKeyUsageList: List<String>,
)
