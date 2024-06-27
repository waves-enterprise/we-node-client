package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.TxId.Companion.txId
import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.Base58Processor

data class LeaseId(val txId: TxId) : SerializableToBytes {
    fun asBase58String(): String =
        txId.asBase58String()

    companion object {
        @JvmStatic
        fun fromTxId(txId: TxId): LeaseId =
            LeaseId(txId)

        @JvmStatic
        fun fromByteArray(bytes: ByteArray): LeaseId =
            LeaseId(
                bytes.txId,
            )

        @JvmStatic
        fun fromBase58(string: String): LeaseId =
            fromByteArray(
                WeBase58.decode(string),
            )

        inline val TxId.leaseId: LeaseId get() = LeaseId(this)

        inline val ByteArray.leaseId: LeaseId get() = fromByteArray(this)

        inline val String.base58LeaseId: LeaseId get() = fromBase58(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = Base58Processor.getBytes(txId.bytes)
}
