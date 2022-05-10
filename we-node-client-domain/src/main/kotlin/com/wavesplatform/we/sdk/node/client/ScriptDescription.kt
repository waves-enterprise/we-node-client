package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class ScriptDescription(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): ScriptDescription =
            ScriptDescription(bytes)

        @JvmStatic
        fun fromBase58(string: String): ScriptDescription =
            ScriptDescription(
                WeBase58.decode(string)
            )

        inline val ByteArray.scriptDescription: ScriptDescription get() = ScriptDescription(this)

        inline val String.base58ScriptDescription: ScriptDescription get() = fromBase58(this)
    }
}
