package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.Base58WithLengthProcessor

data class Attachment(val bytes: ByteArray) : SerializableToBytes {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): Attachment =
            Attachment(bytes)

        @JvmStatic
        fun fromBase58(string: String): Attachment =
            fromByteArray(
                WeBase58.decode(string),
            )

        @Suppress("MemberNameEqualsClassName")
        inline val ByteArray.attachment: Attachment get() = Attachment(this)

        inline val String.base58Attachment: Attachment get() = fromBase58(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = Base58WithLengthProcessor.getBytes(bytes)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Attachment

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
