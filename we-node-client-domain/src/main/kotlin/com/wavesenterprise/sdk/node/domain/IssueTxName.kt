package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.StringProcessor

data class IssueTxName(val bytes: ByteArray) : SerializableToBytes {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): IssueTxName =
            IssueTxName(bytes)

        @JvmStatic
        fun fromBase58(string: String): IssueTxName =
            IssueTxName(
                WeBase58.decode(string),
            )

        @Suppress("MemberNameEqualsClassName")
        inline val ByteArray.issueTxName: IssueTxName get() = IssueTxName(this)

        inline val String.base58IssueTxName: IssueTxName get() = fromBase58(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = StringProcessor.getBytes(WeBase58.encode(bytes))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IssueTxName

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
