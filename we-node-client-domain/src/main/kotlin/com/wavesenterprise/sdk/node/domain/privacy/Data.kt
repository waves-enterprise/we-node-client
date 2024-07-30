package com.wavesenterprise.sdk.node.domain.privacy

import java.util.Base64

data class Data(val bytes: ByteArray) {
    fun asBase64String(): String =
        BASE_64_ENCODER.encodeToString(bytes)

    companion object {
        private val BASE_64_ENCODER = Base64.getEncoder()
        private val BASE_64_DECODER = Base64.getDecoder()

        @JvmStatic
        fun fromByteArray(bytes: ByteArray): Data =
            Data(bytes)

        @JvmStatic
        fun fromBase64(string: String): Data =
            Data(
                BASE_64_DECODER.decode(string),
            )

        @Suppress("MemberNameEqualsClassName")
        inline val ByteArray.data: Data get() = Data(this)

        inline val String.base64Data: Data get() = fromBase64(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
