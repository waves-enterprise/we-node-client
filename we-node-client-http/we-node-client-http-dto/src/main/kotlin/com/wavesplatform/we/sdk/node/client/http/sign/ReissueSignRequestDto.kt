package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.ReissueTxDto
import com.wavesplatform.we.sdk.node.client.sign.ReissueSignRequest

data class ReissueSignRequestDto(
    override val type: Int = TxType.REISSUE.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
    val fee: Long,
    val quantity: Long,
    val assetId: String? = null,
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
                reissuable = reissuable,
            )
    }
}
