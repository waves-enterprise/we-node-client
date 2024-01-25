package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.Base58Processor

data class AssetId(val bytes: ByteArray) : SerializableToBytes {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }

    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): AssetId =
            AssetId(bytes)
        @JvmStatic
        fun fromBase58(string: String): AssetId =
            AssetId(
                WeBase58.decode(string)
            )

        inline val ByteArray.assetId: AssetId get() = AssetId(this)

        inline val String.base58AssetId: AssetId get() = fromBase58(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = Base58Processor.getBytes(bytes)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AssetId

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }
}
