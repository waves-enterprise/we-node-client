package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.ContractApiVersionDto
import com.wavesplatform.we.sdk.node.client.http.ContractApiVersionDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.DataEntryDto
import com.wavesplatform.we.sdk.node.client.http.DataEntryDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.ValidationPolicyDto
import com.wavesplatform.we.sdk.node.client.http.ValidationPolicyDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.CreateContractTxDto
import com.wavesplatform.we.sdk.node.client.sign.CreateContractSignRequest

data class CreateContractSignRequestDto(
    override val type: Int = TxType.CREATE_CONTRACT.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val feeAssetId: String? = null,
    val image: String,
    val imageHash: String,
    val contractName: String,
    val params: List<DataEntryDto>,
    val apiVersion: ContractApiVersionDto? = null,
    val validationPolicy: ValidationPolicyDto? = null,
    val atomicBadge: AtomicBadgeDto? = null,
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
