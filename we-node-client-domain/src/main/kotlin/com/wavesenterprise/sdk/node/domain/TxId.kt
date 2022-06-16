package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58

data class TxId(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): TxId =
            TxId(bytes)

        @JvmStatic
        fun fromBase58(string: String): TxId =
            TxId(
                WeBase58.decode(string)
            )

        inline val ByteArray.txId: TxId get() = fromByteArray(this)

        inline val String.base58TxId: TxId get() = fromBase58(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TxId

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
