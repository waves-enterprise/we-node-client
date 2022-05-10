package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.TxId.Companion.txId
import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class LeaseId(val txId: TxId) {
    fun asBase58String(): String =
        txId.asBase58String()

    companion object {
        @JvmStatic
        fun fromTxId(txId: TxId): LeaseId =
            LeaseId(txId)

        @JvmStatic
        fun fromByteArray(bytes: ByteArray): LeaseId =
            LeaseId(
                bytes.txId
            )

        @JvmStatic
        fun fromBase58(string: String): LeaseId =
            fromByteArray(
                WeBase58.decode(string)
            )

        inline val TxId.leaseId: LeaseId get() = LeaseId(this)

        inline val ByteArray.leaseId: LeaseId get() = fromByteArray(this)

        inline val String.base58LeaseId: LeaseId get() = fromBase58(this)
    }
}
