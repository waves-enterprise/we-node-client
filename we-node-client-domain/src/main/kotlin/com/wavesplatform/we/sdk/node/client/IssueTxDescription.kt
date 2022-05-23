package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

data class IssueTxDescription(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): IssueTxDescription =
            IssueTxDescription(bytes)

        @JvmStatic
        fun fromBase58(string: String): IssueTxDescription =
            IssueTxDescription(
                WeBase58.decode(string)
            )

        inline val ByteArray.issueTxDescription: IssueTxDescription get() = IssueTxDescription(this)

        inline val String.base58IssueTxDescription: IssueTxDescription get() = fromBase58(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IssueTxDescription

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
