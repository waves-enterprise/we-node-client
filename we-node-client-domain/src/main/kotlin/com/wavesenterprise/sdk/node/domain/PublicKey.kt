package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes

data class PublicKey(val bytes: ByteArray) : SerializableToBytes {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): PublicKey =
            PublicKey(bytes)

        @JvmStatic
        fun fromBase58(string: String): PublicKey =
            PublicKey(
                WeBase58.decode(string),
            )

        @Suppress("MemberNameEqualsClassName")
        inline val ByteArray.publicKey: PublicKey get() = PublicKey(this)

        inline val String.base58PublicKey: TxId get() = TxId.fromBase58(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = bytes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PublicKey

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
