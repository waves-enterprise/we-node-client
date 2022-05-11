package com.wavesplatform.we.sdk.node.client.http.privacy

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.http.privacy.PolicyItemFileInfoDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.privacy.PolicyItemInfoResponse

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
    }
}
