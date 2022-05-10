package com.wavesplatform.we.sdk.node.client

import java.util.Locale

@JvmInline
value class Hash(val bytes: ByteArray) {
    fun asHexString(): String =
        bytes.joinToString("") { byte: Byte ->
            "%02x".format(byte)
        }

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): Hash =
            Hash(bytes)

        @JvmStatic
        fun fromHexString(hexStr: String): Hash {
            check(hexStr.length % 2 == 0) {
                "Hex string must have an even length"
            }
            return Hash(
                hexStr
                    .chunked(2)
                    .map {
                        it.uppercase(Locale.getDefault()).toInt(16).toByte()
                    }
                    .toByteArray()
            )
        }

        inline val ByteArray.hash: Hash get() = Hash(this)

        inline val String.hexStrHash: Hash get() = fromHexString(this)
    }
}
