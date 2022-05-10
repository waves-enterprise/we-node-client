package com.wavesplatform.we.sdk.node.client.http

import com.wavesplatform.we.sdk.node.client.ContractApiVersion
import com.wavesplatform.we.sdk.node.client.MajorVersion
import com.wavesplatform.we.sdk.node.client.MinorVersion

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
