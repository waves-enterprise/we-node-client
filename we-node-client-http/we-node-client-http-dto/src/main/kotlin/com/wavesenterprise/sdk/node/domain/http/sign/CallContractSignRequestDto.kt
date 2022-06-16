package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.DataEntryDto
import com.wavesenterprise.sdk.node.domain.http.DataEntryDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.tx.CreateContractTxDto
import com.wavesenterprise.sdk.node.domain.sign.CallContractSignRequest

data class CallContractSignRequestDto(
    override val type: Int = TxType.CALL_CONTRACT.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val feeAssetId: String?,
    val contractId: String,
    val params: List<DataEntryDto>,
    val atomicBadge: AtomicBadgeDto?,
) : SignRequestDto<CreateContractTxDto> {
    companion object {
        @JvmStatic
        fun CallContractSignRequest.toDto(): CallContractSignRequestDto =
            CallContractSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                contractId = contractId.asBase58String(),
                params = params.map { it.toDto() },
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
