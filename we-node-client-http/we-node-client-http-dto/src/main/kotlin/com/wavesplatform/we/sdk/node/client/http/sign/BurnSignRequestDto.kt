package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.BurnTxDto
import com.wavesplatform.we.sdk.node.client.sign.BurnSignRequest

data class BurnSignRequestDto(
    override val type: Int = TxType.BURN.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val assetId: String? = null,
    val quantity: Long,
    val attachment: String? = null,
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
