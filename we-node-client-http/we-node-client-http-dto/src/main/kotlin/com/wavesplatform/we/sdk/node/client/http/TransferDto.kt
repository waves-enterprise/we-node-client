package com.wavesplatform.we.sdk.node.client.http

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.Transfer

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
