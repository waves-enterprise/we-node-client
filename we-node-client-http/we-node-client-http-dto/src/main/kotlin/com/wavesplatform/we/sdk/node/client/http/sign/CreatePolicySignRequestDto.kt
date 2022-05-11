package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.CreatePolicyTxDto
import com.wavesplatform.we.sdk.node.client.sign.CreatePolicySignRequest

data class CreatePolicySignRequestDto(
    override val type: Int = TxType.CREATE_POLICY.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val feeAssetId: String?,
    val policyName: String,
    val recipients: List<String>,
    val owners: List<String>,
    val description: String,
    val atomicBadge: AtomicBadgeDto?,
) : SignRequestDto<CreatePolicyTxDto> {
    companion object {
        @JvmStatic
        fun CreatePolicySignRequest.toDto(): CreatePolicySignRequestDto =
            CreatePolicySignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                policyName = policyName.value,
                recipients = recipients.map { it.asBase58String() },
                owners = owners.map { it.asBase58String() },
                description = description.value,
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
