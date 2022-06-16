package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.OpTypeConstants.toDto
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.tx.UpdatePolicyTxDto
import com.wavesenterprise.sdk.node.domain.sign.UpdatePolicySignRequest

data class UpdatePolicySignRequestDto(
    override val type: Int = TxType.UPDATE_POLICY.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val feeAssetId: String?,
    val policyId: String,
    val opType: String,
    val recipients: List<String>,
    val owners: List<String>,
    val atomicBadge: AtomicBadgeDto?,
) : SignRequestDto<UpdatePolicyTxDto> {
    companion object {
        @JvmStatic
        fun UpdatePolicySignRequest.toDto(): UpdatePolicySignRequestDto =
            UpdatePolicySignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                policyId = policyId.asBase58String(),
                opType = opType.toDto(),
                recipients = recipients.map { it.asBase58String() },
                owners = owners.map { it.asBase58String() },
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
