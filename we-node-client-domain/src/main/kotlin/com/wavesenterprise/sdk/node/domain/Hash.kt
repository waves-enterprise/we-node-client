package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58

data class Hash(val bytes: ByteArray) {
    fun asBase58String(): String = WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): Hash =
            Hash(bytes)

        @JvmStatic
        fun fromStringBase58(base58HashString: String): Hash =
            Hash(WeBase58.decode(base58HashString))

        @Suppress("MemberNameEqualsClassName")
        inline val ByteArray.hash: Hash get() = Hash(this)

        inline val String.base58StrHash: Hash get() = fromStringBase58(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hash

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
