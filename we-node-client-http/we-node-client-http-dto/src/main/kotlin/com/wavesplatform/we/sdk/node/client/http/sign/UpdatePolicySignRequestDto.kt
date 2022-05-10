package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.OpTypeConstants.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.UpdatePolicyTxDto
import com.wavesplatform.we.sdk.node.client.sign.UpdatePolicySignRequest

data class UpdatePolicySignRequestDto(
    override val type: Int = TxType.UPDATE_POLICY.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val feeAssetId: String? = null,
    val policyId: String,
    val opType: String,
    val recipients: List<String>,
    val owners: List<String>,
    val atomicBadge: AtomicBadgeDto? = null,
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
