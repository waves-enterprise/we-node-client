package com.wavesenterprise.sdk.node.client.http.sign

import com.wavesenterprise.sdk.node.client.http.ContractApiVersionDto
import com.wavesenterprise.sdk.node.client.http.ContractApiVersionDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.DataEntryDto
import com.wavesenterprise.sdk.node.client.http.DataEntryDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.ValidationPolicyDto
import com.wavesenterprise.sdk.node.client.http.ValidationPolicyDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.CreateContractTxDto
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.sign.CreateContractSignRequest

data class CreateContractSignRequestDto(
    override val type: Int = TxType.CREATE_CONTRACT.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val feeAssetId: String?,
    val image: String,
    val imageHash: String,
    val contractName: String,
    val params: List<DataEntryDto>,
    val apiVersion: ContractApiVersionDto?,
    val validationPolicy: ValidationPolicyDto?,
    val atomicBadge: AtomicBadgeDto?,
) : SignRequestDto<CreateContractTxDto> {
    companion object {
        @JvmStatic
        fun CreateContractSignRequest.toDto(): CreateContractSignRequestDto =
            CreateContractSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                image = image.value,
                imageHash = imageHash.asHexString(),
                contractName = contractName.value,
                params = params.map { it.toDto() },
                apiVersion = apiVersion?.toDto(),
                validationPolicy = validationPolicy?.toDto(),
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
