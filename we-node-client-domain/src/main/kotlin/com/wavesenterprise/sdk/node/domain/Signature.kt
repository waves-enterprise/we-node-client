package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58

data class Signature(val bytes: ByteArray) {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Signature

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
