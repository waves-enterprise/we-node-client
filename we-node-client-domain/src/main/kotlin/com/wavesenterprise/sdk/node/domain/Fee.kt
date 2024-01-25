package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.LongProcessor

data class Fee(val value: Long) : SerializableToBytes {
    init {
        check(value >= 0) {
            "Fee value should be a non-negative number"
        }
    }

    companion object {
        @JvmStatic
        fun fromLong(value: Long): Fee =
            Fee(value)

        @JvmStatic
        fun fromInt(value: Int): Fee =
            Fee(value.toLong())

        inline val Long.fee: Fee get() = Fee(this)

        inline val Int.fee: Fee get() = Fee(this.toLong())
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = LongProcessor.getBytes(value)
}
