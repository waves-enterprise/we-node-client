package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.client.http.tx.AtomicInnerTxDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.tx.AtomicInnerTxDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx

data class AtomicTxDto(
    override val id: String,
    override val type: Int = TxType.ATOMIC.code,
    val senderPublicKey: String,
    val miner: String?,
    val transactions: List<AtomicInnerTxDto>,
    override val timestamp: Long,
    val proofs: List<String>?,
    val sender: String,
    val fee: Long,
    override val version: Int,
    override val height: Long? = null,
) : TxDto {
    companion object {
        @JvmStatic
        fun AtomicTx.toDto(): AtomicTxDto =
            AtomicTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                miner = miner?.asBase58String(),
                transactions = txs.map { it.toDto() },
                timestamp = timestamp.utcTimestampMillis,
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                fee = fee.value,
                version = version.value,
            )

        @JvmStatic
        fun AtomicTxDto.toDomain(): AtomicTx =
            AtomicTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                miner = miner?.let { Address.fromBase58(it) },
                txs = transactions.map { it.toDomain() },
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                fee = Fee(fee),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: AtomicTxDto): AtomicTx =
            tx.toDomain()
    }
}
