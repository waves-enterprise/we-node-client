package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.TxId.Companion.txId
import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.Base58Processor

data class FeeAssetId(val txId: TxId) : SerializableToBytes {
    fun asBase58String(): String =
        txId.asBase58String()

    companion object {
        @JvmStatic
        fun fromTxId(txId: TxId): FeeAssetId =
            FeeAssetId(txId)

        @JvmStatic
        fun fromByteArray(bytes: ByteArray): FeeAssetId =
            FeeAssetId(
                bytes.txId,
            )

        @JvmStatic
        fun fromBase58(string: String): FeeAssetId =
            fromByteArray(
                WeBase58.decode(string),
            )

        @Suppress("MemberNameEqualsClassName")
        inline val TxId.feeAssetId: FeeAssetId get() = FeeAssetId(this)

        @Suppress("MemberNameEqualsClassName")
        inline val ByteArray.feeAssetId: FeeAssetId get() = fromByteArray(this)

        inline val String.base58FeeAssetId: FeeAssetId get() = fromBase58(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = Base58Processor.getBytes(txId.bytes)
}
