package com.wavesenterprise.sdk.node.client.http.privacy

import com.wavesenterprise.sdk.node.client.http.privacy.PolicyItemFileInfoDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.privacy.PolicyItemFileInfoDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.privacy.PolicyItemInfoResponse

data class PolicyItemInfoResponseDto(
    val sender: String,
    val policy: String,
    val info: PolicyItemFileInfoDto,
    val hash: String,
) {
    companion object {
        @JvmStatic
        fun PolicyItemInfoResponseDto.toDomain(): PolicyItemInfoResponse =
            PolicyItemInfoResponse(
                senderAddress = Address.fromBase58(sender),
                policyId = PolicyId.fromBase58(policy),
                info = info.toDomain(),
                dataHash = Hash.fromHexString(hash),
            )

        @JvmStatic
        fun PolicyItemInfoResponse.toDto(): PolicyItemInfoResponseDto =
            PolicyItemInfoResponseDto(
                sender = senderAddress.asBase58String(),
                policy = policyId.asBase58String(),
                info = info.toDto(),
                hash = dataHash.asHexString(),
            )
    }
}
