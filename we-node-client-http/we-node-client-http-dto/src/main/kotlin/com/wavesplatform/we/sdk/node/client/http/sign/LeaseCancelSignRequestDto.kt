package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.LeaseCancelTxDto
import com.wavesplatform.we.sdk.node.client.sign.LeaseCancelSignRequest

data class LeaseCancelSignRequestDto(
    override val type: Int = TxType.LEASE_CANCEL.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val leaseId: String,
) : SignRequestDto<LeaseCancelTxDto> {
    companion object {
        @JvmStatic
        fun LeaseCancelSignRequest.toDto(): LeaseCancelSignRequestDto =
            LeaseCancelSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                leaseId = leaseId.asBase58String(),
            )
    }
}
