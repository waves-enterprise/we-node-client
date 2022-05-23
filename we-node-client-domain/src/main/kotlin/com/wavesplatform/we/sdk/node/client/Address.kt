package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

data class Address(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }
}
