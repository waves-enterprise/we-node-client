package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class TxId(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): TxId =
            TxId(bytes)

        @JvmStatic
        fun fromBase58(string: String): TxId =
            TxId(
                WeBase58.decode(string)
            )

        inline val ByteArray.txId: TxId get() = fromByteArray(this)

        inline val String.base58TxId: TxId get() = fromBase58(this)
    }
}
