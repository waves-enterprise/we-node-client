package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.LeaseTxDto
import com.wavesplatform.we.sdk.node.client.sign.LeaseSignRequest

data class LeaseSignRequestDto(
    override val type: Int = TxType.LEASE.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
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
