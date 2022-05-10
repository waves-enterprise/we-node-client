package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.TransferTxDto
import com.wavesplatform.we.sdk.node.client.sign.TransferSignRequest

data class TransferSignRequestDto(
    override val type: Int = TxType.TRANSFER.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val recipient: String,
    val amount: Long,
    val atomicBadge: AtomicBadgeDto?,
) : SignRequestDto<TransferTxDto> {
    companion object {
        @JvmStatic
        fun TransferSignRequest.toDto(): TransferSignRequestDto =
            TransferSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                recipient = recipient.asBase58String(),
                amount = amount.value,
                atomicBadge = atomicBadge?.toDto(),
            )
    }
}
