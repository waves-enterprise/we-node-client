package com.wavesenterprise.sdk.node.domain.contract

import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxId.Companion.txId
import com.wavesenterprise.sdk.node.domain.base58.WeBase58
import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.Base58WithLengthProcessor

data class ContractId(val txId: TxId) : SerializableToBytes {
    fun asBase58String(): String =
        txId.asBase58String()

    companion object {
        @JvmStatic
        fun fromTxId(txId: TxId): ContractId =
            ContractId(txId)

        @JvmStatic
        fun fromByteArray(bytes: ByteArray): ContractId =
            ContractId(
                bytes.txId
            )

        @JvmStatic
        fun fromBase58(string: String): ContractId =
            fromByteArray(
                WeBase58.decode(string)
            )

        inline val TxId.contractId: ContractId get() = ContractId(this)

        inline val ByteArray.contractId: ContractId get() = fromByteArray(this)

        inline val String.base58ContractId: ContractId get() = fromBase58(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = Base58WithLengthProcessor.getBytes(txId.bytes)
}
