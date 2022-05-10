package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.PermitDataEntryDto
import com.wavesplatform.we.sdk.node.client.http.PermitDataEntryDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.DataTxDto
import com.wavesplatform.we.sdk.node.client.sign.DataSignRequest

data class DataSignRequestDto(
    override val type: Int = TxType.DATA.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val feeAssetId: String? = null,
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
