package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.ALIAS_VERSION
import com.wavesenterprise.sdk.node.domain.util.processor.concatBytes
import com.wavesenterprise.sdk.node.domain.util.processor.numberToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.strToBytes

data class Address(val bytes: ByteArray) : SerializableToBytes {
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

        val aliasRegex = Regex("alias:.:", RegexOption.IGNORE_CASE)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray {
        val stringAddress = WeBase58.encode(bytes)
        return if (aliasRegex.matches(stringAddress)) {
            if (networkByte == null) {
                throw IllegalStateException("Cannot create tx signature. Network byte is required")
            }
            val alias = stringAddress.split(':').last()
            val aliasBytes = strToBytes(alias)
            concatBytes(
                byteArrayOf(ALIAS_VERSION),
                byteArrayOf(networkByte),
                numberToBytes(aliasBytes.size),
                aliasBytes
            )
        } else {
            bytes
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }
}
