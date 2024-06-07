package com.wavesenterprise.sdk.node.client.http.sign

import com.wavesenterprise.sdk.node.client.http.DataEntryDto
import com.wavesenterprise.sdk.node.client.http.DataEntryDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.tx.DataTxDto
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.sign.DataSignRequest

data class DataSignRequestDto(
    override val type: Int = TxType.DATA.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val feeAssetId: String?,
    val author: String,
    val data: List<DataEntryDto>,
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
