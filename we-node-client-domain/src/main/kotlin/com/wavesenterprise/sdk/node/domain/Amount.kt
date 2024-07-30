package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.LongProcessor

data class Amount(val value: Long) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromLong(value: Long): Amount =
            Amount(value)

        @Suppress("MemberNameEqualsClassName")
        inline val Long.amount: Amount get() = Amount(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = LongProcessor.getBytes(value)
}
