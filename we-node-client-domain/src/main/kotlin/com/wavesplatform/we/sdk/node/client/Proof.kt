package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class Proof(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): Proof =
            Proof(bytes)

        @JvmStatic
        fun fromBase58(string: String): Proof =
            Proof.fromByteArray(
                WeBase58.decode(string)
            )

        inline val String.base58Proof: Proof get() = Proof.fromBase58(this)
    }
}
