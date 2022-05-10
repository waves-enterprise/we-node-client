package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class IssueTxName(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): IssueTxName =
            IssueTxName(bytes)

        @JvmStatic
        fun fromBase58(string: String): IssueTxName =
            IssueTxName(
                WeBase58.decode(string)
            )

        inline val ByteArray.issueTxName: IssueTxName get() = IssueTxName(this)

        inline val String.base58IssueTxName: IssueTxName get() = fromBase58(this)
    }
}
