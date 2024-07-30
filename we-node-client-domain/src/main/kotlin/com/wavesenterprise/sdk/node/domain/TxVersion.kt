package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes

data class TxVersion(val value: Int) : SerializableToBytes {
    companion object {
        @JvmStatic
        @JvmName("fromInt")
        fun fromInt(value: Int): TxVersion =
            TxVersion(value)

        @Suppress("MemberNameEqualsClassName")
        inline val Int.txVersion: TxVersion get() = TxVersion(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = byteArrayOf(value.toByte())
}
