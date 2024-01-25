package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.LongProcessor

data class Quantity(val value: Long) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromLong(value: Long): Quantity =
            Quantity(value)

        inline val Long.quantity: Quantity get() = Quantity(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = LongProcessor.getBytes(value)
}
