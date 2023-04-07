package com.wavesenterprise.sdk.node.client.http.sign

import com.wavesenterprise.sdk.node.client.http.tx.BurnTxDto
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.sign.BurnSignRequest

data class BurnSignRequestDto(
    override val type: Int = TxType.BURN.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val assetId: String?,
    val quantity: Long,
    val attachment: String?,
) : SignRequestDto<BurnTxDto> {
    companion object {
        @JvmStatic
        fun BurnSignRequest.toDto(): BurnSignRequestDto =
            BurnSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                assetId = assetId?.asBase58String(),
                quantity = quantity.value,
                attachment = attachment?.asBase58String(),
            )
    }
}
