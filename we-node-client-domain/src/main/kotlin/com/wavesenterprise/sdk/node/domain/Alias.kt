package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.ALIAS_VERSION
import com.wavesenterprise.sdk.node.domain.util.processor.concatBytes
import com.wavesenterprise.sdk.node.domain.util.processor.numberToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.strToBytes

data class Alias(val value: String) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromString(str: String): Alias =
            Alias(str)

        @JvmStatic
        fun String.toDomain(): Alias = fromString(this.substring(ALIAS_START_INDEX))

        private const val ALIAS_START_INDEX = 8

        @Suppress("MemberNameEqualsClassName")
        inline val String.alias: Alias get() = Alias(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray {
        val aliasBytes = strToBytes(value)
        return concatBytes(
            byteArrayOf(ALIAS_VERSION),
            byteArrayOf(requireNotNull(networkByte)),
            numberToBytes(aliasBytes.size, 2),
            aliasBytes,
        )
    }
}
