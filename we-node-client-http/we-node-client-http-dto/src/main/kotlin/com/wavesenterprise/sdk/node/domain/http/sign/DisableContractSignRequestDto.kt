package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.tx.DisableContractTxDto
import com.wavesenterprise.sdk.node.domain.sign.DisableContractSignRequest

data class DisableContractSignRequestDto(
    override val type: Int = TxType.DISABLE_CONTRACT.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val contractId: String,
    val feeAssetId: String?,
    val atomicBadge: AtomicBadgeDto?,
) : SignRequestDto<DisableContractTxDto> {
    companion object {
        @JvmStatic
        fun DisableContractSignRequest.toDto(): DisableContractSignRequestDto =
            DisableContractSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                contractId = contractId.asBase58String(),
                feeAssetId = feeAssetId?.asBase58String(),
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
