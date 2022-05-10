package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class Attachment(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): Attachment =
            Attachment(bytes)

        @JvmStatic
        fun fromBase58(string: String): Attachment =
            fromByteArray(
                WeBase58.decode(string)
            )

        inline val ByteArray.attachment: Attachment get() = Attachment(this)

        inline val String.base58Attachment: Attachment get() = fromBase58(this)
    }
}
