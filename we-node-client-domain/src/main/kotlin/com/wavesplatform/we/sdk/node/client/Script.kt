package com.wavesplatform.we.sdk.node.client

import java.util.Base64

@JvmInline
value class Script(val bytes: ByteArray) {
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
}
