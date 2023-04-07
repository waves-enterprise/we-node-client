package com.wavesenterprise.sdk.node.client.http

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.Transfer

data class TransferDto(
    val recipient: String,
    val amount: Long,
) {
    companion object {
        @JvmStatic
        fun Transfer.toDto(): TransferDto =
            TransferDto(
                recipient = recipient.asBase58String(),
                amount = amount.value,
            )

        @JvmStatic
        fun TransferDto.toDomain(): Transfer =
            Transfer(
                recipient = Address.fromBase58(recipient),
                amount = Amount(amount),
            )
    }
}
