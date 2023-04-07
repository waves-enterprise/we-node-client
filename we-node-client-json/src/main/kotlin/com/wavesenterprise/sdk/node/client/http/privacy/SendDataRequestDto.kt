package com.wavesenterprise.sdk.node.client.http.privacy

import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.privacy.PolicyItemFileInfoDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.privacy.SendDataRequest
import java.util.Base64

data class SendDataRequestDto(
    val sender: String,
    val policyId: String,
    val dataHash: String,
    val data: String,
    val info: PolicyItemFileInfoDto,
    val fee: Long,
    val feeAssetId: String? = null,
    val atomicBadge: AtomicBadgeDto? = null,
    val password: String? = null,
) {
    companion object {
        private val BASE_64_ENCODER = Base64.getEncoder()

        @JvmStatic
        fun SendDataRequest.toDto(): SendDataRequestDto =
            SendDataRequestDto(
                sender = senderAddress.asBase58String(),
                policyId = policyId.asBase58String(),
                dataHash = dataHash.asHexString(),
                data = BASE_64_ENCODER.encodeToString(data.bytes),
                info = info.toDto(),
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                atomicBadge = atomicBadge?.toDto(),
                password = password?.value,
            )
    }
}
