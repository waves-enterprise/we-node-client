package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.ContractApiVersionDto
import com.wavesenterprise.sdk.node.domain.http.ContractApiVersionDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.ValidationPolicyDto
import com.wavesenterprise.sdk.node.domain.http.ValidationPolicyDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.tx.UpdateContractTxDto
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
    val apiVersion: ContractApiVersionDto?,
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
                imageHash = imageHash.asHexString(),
                apiVersion = apiVersion?.toDto(),
                validationPolicy = validationPolicy?.toDto(),
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
