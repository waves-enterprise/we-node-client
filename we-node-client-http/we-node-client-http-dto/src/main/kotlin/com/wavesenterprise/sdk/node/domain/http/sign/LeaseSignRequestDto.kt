package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.tx.LeaseTxDto
import com.wavesenterprise.sdk.node.domain.sign.LeaseSignRequest

data class LeaseSignRequestDto(
    override val type: Int = TxType.LEASE.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val recipient: String,
    val amount: Long,
) : SignRequestDto<LeaseTxDto> {
    companion object {
        @JvmStatic
        fun LeaseSignRequest.toDto(): LeaseSignRequestDto =
            LeaseSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                recipient = recipient.asBase58String(),
                amount = amount.value,
            )
    }
}
