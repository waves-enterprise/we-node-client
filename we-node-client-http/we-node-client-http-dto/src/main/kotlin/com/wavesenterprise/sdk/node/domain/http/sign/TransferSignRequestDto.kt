package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.domain.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.tx.TransferTxDto
import com.wavesenterprise.sdk.node.domain.sign.TransferSignRequest

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
