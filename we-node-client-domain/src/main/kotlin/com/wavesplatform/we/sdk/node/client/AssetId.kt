package com.wavesplatform.we.sdk.node.client

import com.wavesplatform.we.sdk.node.client.base58.WeBase58

@JvmInline
value class AssetId(val bytes: ByteArray) {
    fun asBase58String(): String =
        WeBase58.encode(bytes)

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
}
