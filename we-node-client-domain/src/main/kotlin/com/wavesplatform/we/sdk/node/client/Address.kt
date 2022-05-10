package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class Address(private val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    fun asByteArray(): ByteArray =
        bytes

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): Address =
            Address(bytes)

        @JvmStatic
        fun fromBase58(string: String): Address =
            fromByteArray(
                WeBase58.decode(string)
            )

        inline val ByteArray.address: Address get() = Address(this)

        inline val String.base58Address: Address get() = fromBase58(this)
    }
}
