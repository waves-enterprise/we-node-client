package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

data class ScriptName(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): ScriptName =
            ScriptName(bytes)

        @JvmStatic
        fun fromBase58(string: String): ScriptName =
            ScriptName(
                WeBase58.decode(string)
            )

        inline val ByteArray.scriptName: ScriptName get() = ScriptName(this)

        inline val String.base58ScriptName: ScriptName get() = ScriptName.fromBase58(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScriptName

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
