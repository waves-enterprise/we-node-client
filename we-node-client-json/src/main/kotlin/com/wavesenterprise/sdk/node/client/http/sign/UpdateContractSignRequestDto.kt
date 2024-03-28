package com.wavesenterprise.sdk.node.client.http.sign

import com.wavesenterprise.sdk.node.client.http.ValidationPolicyDto
import com.wavesenterprise.sdk.node.client.http.ValidationPolicyDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.UpdateContractTxDto
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.sign.UpdateContractSignRequest

data class UpdateContractSignRequestDto(
    override val type: Int = TxType.UPDATE_CONTRACT.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val feeAssetId: String?,
    val contractId: String,
    val image: String,
    val imageHash: String,
    val apiVersion: String?,
    val validationPolicy: ValidationPolicyDto?,
    val atomicBadge: AtomicBadgeDto?,
) : SignRequestDto<UpdateContractTxDto> {
    companion object {
        @JvmStatic
        fun UpdateContractSignRequest.toDto(): UpdateContractSignRequestDto =
            UpdateContractSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                contractId = contractId.asBase58String(),
                image = image.value,
                imageHash = imageHash.value,
                apiVersion = apiVersion?.let { "${it.major.value}.${it.minor.value}" },
                validationPolicy = validationPolicy?.toDto(),
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
