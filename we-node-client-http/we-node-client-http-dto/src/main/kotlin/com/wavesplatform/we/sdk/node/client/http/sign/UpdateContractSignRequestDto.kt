package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.ContractApiVersionDto
import com.wavesplatform.we.sdk.node.client.http.ContractApiVersionDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.ValidationPolicyDto
import com.wavesplatform.we.sdk.node.client.http.ValidationPolicyDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.UpdateContractTxDto
import com.wavesplatform.we.sdk.node.client.sign.UpdateContractSignRequest

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
