package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.TxId.Companion.txId
import com.wavesenterprise.sdk.node.domain.base58.WeBase58

data class FeeAssetId(val txId: TxId) {
    fun asBase58String(): String =
        txId.asBase58String()

    companion object {
        @JvmStatic
        fun fromTxId(txId: TxId): FeeAssetId =
            FeeAssetId(txId)

        @JvmStatic
        fun fromByteArray(bytes: ByteArray): FeeAssetId =
            FeeAssetId(
                bytes.txId
            )

        @JvmStatic
        fun fromBase58(string: String): FeeAssetId =
            fromByteArray(
                WeBase58.decode(string)
            )

        inline val TxId.feeAssetId: FeeAssetId get() = FeeAssetId(this)

        inline val ByteArray.feeAssetId: FeeAssetId get() = fromByteArray(this)

        inline val String.base58FeeAssetId: FeeAssetId get() = fromBase58(this)
    }
}
