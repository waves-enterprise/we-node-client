package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.SponsorFeeTxDto
import com.wavesplatform.we.sdk.node.client.sign.SponsorFeeSignRequest

data class SponsorFeeSignRequestDto(
    override val type: Int = TxType.SPONSOR_FEE.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val enabled: Boolean,
    val assetId: String?,
) : SignRequestDto<SponsorFeeTxDto> {
    companion object {
        @JvmStatic
        fun SponsorFeeSignRequest.toDto(): SponsorFeeSignRequestDto =
            SponsorFeeSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                enabled = enabled,
                assetId = assetId?.asBase58String(),
            )
    }
}
