package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58

data class ScriptDescription(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): ScriptDescription =
            ScriptDescription(bytes)

        @JvmStatic
        fun fromBase58(string: String): ScriptDescription =
            ScriptDescription(
                WeBase58.decode(string),
            )

        inline val ByteArray.scriptDescription: ScriptDescription get() = ScriptDescription(this)

        inline val String.base58ScriptDescription: ScriptDescription get() = fromBase58(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScriptDescription

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
