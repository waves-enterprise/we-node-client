package com.wavesenterprise.sdk.node.domain

import java.util.Base64

data class Script(val bytes: ByteArray) {
    fun asBase64String(): String =
        BASE_64_ENCODER.encodeToString(bytes)

    companion object {
        private val BASE_64_ENCODER = Base64.getEncoder()
        private val BASE_64_DECODER = Base64.getDecoder()

        @JvmStatic
        fun fromByteArray(bytes: ByteArray): Script =
            Script(bytes)

        @JvmStatic
        fun fromBase64(string: String): Script =
            Script(
                BASE_64_DECODER.decode(string)
            )

        inline val ByteArray.script: Script get() = Script(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Script

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
