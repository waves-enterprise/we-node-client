package com.wavesenterprise.sdk.node.client.http.sign

import com.wavesenterprise.sdk.node.client.http.tx.SponsorFeeTxDto
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.sign.SponsorFeeSignRequest

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
                enabled = enabled.value,
                assetId = assetId?.asBase58String(),
            )
    }
}
