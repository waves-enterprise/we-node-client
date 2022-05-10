package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.CreatePolicyTxDto
import com.wavesplatform.we.sdk.node.client.sign.CreatePolicySignRequest

data class CreatePolicySignRequestDto(
    override val type: Int = TxType.CREATE_POLICY.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val feeAssetId: String? = null,
    val policyName: String,
    val recipients: List<String>,
    val owners: List<String>,
    val description: String,
    val atomicBadge: AtomicBadgeDto? = null,
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
