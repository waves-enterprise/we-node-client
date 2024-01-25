package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes

data class ChainId(val value: Byte) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromByte(value: Byte): ChainId =
            ChainId(value)

        inline val Byte.chainId: ChainId get() = ChainId(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = byteArrayOf(value)
}
