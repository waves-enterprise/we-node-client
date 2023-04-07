package com.wavesenterprise.sdk.node.client.http

import com.wavesenterprise.sdk.node.domain.ContractApiVersion
import com.wavesenterprise.sdk.node.domain.MajorVersion
import com.wavesenterprise.sdk.node.domain.MinorVersion

data class ContractApiVersionDto(
    val major: Int,
    val minor: Int,
) {
    companion object {
        @JvmStatic
        fun ContractApiVersion.toDto(): ContractApiVersionDto =
            ContractApiVersionDto(
                major = major.value,
                minor = minor.value,
            )

        @JvmStatic
        fun ContractApiVersionDto.toDomain(): ContractApiVersion =
            ContractApiVersion(
                major = MajorVersion(major),
                minor = MinorVersion(minor),
            )
    }
}
