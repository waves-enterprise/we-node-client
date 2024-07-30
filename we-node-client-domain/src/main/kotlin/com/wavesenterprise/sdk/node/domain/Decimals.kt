package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes

data class Decimals(val value: Byte) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromByte(value: Byte): Decimals =
            Decimals(value)

        @Suppress("MemberNameEqualsClassName")
        inline val Byte.decimals: Decimals get() = Decimals(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = byteArrayOf(value)
}
