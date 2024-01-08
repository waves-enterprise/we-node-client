package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58

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

        @JvmStatic
        fun String.toDomain(): Address = Address.fromBase58(this)

        inline val ByteArray.address: Address get() = Address(this)

        inline val String.base58Address: Address get() = fromBase58(this)

        val EMPTY = Address(ByteArray(0))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }
}
