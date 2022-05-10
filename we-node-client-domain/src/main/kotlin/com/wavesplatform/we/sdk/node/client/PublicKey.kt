package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class PublicKey(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): PublicKey =
            PublicKey(bytes)

        @JvmStatic
        fun fromBase58(string: String): PublicKey =
            PublicKey(
                WeBase58.decode(string)
            )

        inline val ByteArray.publicKey: PublicKey get() = PublicKey(this)

        inline val String.base58PublicKey: TxId get() = TxId.fromBase58(this)
    }
}
