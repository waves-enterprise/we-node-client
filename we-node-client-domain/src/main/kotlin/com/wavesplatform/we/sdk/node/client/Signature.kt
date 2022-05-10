package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class Signature(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): Signature =
            Signature(bytes)

        @JvmStatic
        fun fromBase58(string: String): Signature =
            WeBase58.decode(string)
                .let(::Signature)

        inline val ByteArray.signature: Signature get() = Signature(this)

        inline val String.base58Signature: Signature get() = fromBase58(this)
    }
}
