package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.tx.GenesisTx

data class GenesisTxDto(
    override val id: String,
    override val type: Int = TxType.GENESIS.code,
    val recipient: String,
    val amount: Long,
    val fee: Long,
    override val timestamp: Long,
    val signature: String,
) : TxDto {
    companion object {
        @JvmStatic
        fun GenesisTx.toDto(): GenesisTxDto =
            GenesisTxDto(
                id = id.asBase58String(),
                recipient = recipient.asBase58String(),
                amount = amount.value,
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                signature = signature.asBase58String(),
            )

        @JvmStatic
        fun GenesisTxDto.toDomain(): GenesisTx =
            GenesisTx(
                id = TxId.fromBase58(id),
                recipient = Address.fromBase58(recipient),
                amount = Amount(amount),
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                signature = Signature.fromBase58(signature),
            )
    }
}
