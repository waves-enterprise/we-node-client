package com.wavesplatform.we.sdk.node.client.privacy

import java.util.Base64

@JvmInline
value class Data(val bytes: ByteArray) {
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
                BASE_64_DECODER.decode(string)
            )

        inline val ByteArray.data: Data get() = Data(this)

        inline val String.base64Data: Data get() = fromBase64(this)
    }
}
