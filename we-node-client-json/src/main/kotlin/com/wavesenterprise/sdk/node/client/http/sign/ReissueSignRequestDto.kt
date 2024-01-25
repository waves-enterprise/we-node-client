package com.wavesenterprise.sdk.node.client.http.sign

import com.wavesenterprise.sdk.node.client.http.tx.ReissueTxDto
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.sign.ReissueSignRequest

data class ReissueSignRequestDto(
    override val type: Int = TxType.REISSUE.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val quantity: Long,
    val assetId: String?,
    val reissuable: Boolean,
) : SignRequestDto<ReissueTxDto> {
    companion object {
        @JvmStatic
        fun ReissueSignRequest.toDto(): ReissueSignRequestDto =
            ReissueSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                quantity = quantity.value,
                assetId = assetId?.asBase58String(),
                reissuable = reissuable.value,
            )
    }
}
