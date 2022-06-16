package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.PermitDataEntryDto
import com.wavesenterprise.sdk.node.domain.http.PermitDataEntryDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.tx.DataTxDto
import com.wavesenterprise.sdk.node.domain.sign.DataSignRequest

data class DataSignRequestDto(
    override val type: Int = TxType.DATA.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val feeAssetId: String?,
    val author: String,
    val data: List<PermitDataEntryDto>,
) : SignRequestDto<DataTxDto> {
    companion object {
        @JvmStatic
        fun DataSignRequest.toDto(): DataSignRequestDto =
            DataSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                author = author.asBase58String(),
                data = data.map { it.toDto() },
            )
    }
}
