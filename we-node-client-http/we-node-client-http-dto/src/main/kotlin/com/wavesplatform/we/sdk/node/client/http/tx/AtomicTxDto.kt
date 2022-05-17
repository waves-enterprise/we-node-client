package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.http.tx.AtomicInnerTxDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.tx.AtomicInnerTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.tx.AtomicTx

data class AtomicTxDto(
    override val id: String,
    override val type: Int = TxType.ATOMIC.code,
    val senderPublicKey: String,
    val miner: String?,
    val txs: List<AtomicInnerTxDto>,
    override val timestamp: Long,
    val proofs: List<String>?,
    val sender: String,
    val fee: Long,
    override val version: Int,
) : TxDto {
    companion object {
        @JvmStatic
        fun AtomicTx.toDto(): AtomicTxDto =
            AtomicTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                miner = miner?.asBase58String(),
                txs = txs.map { it.toDto() },
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
                txs = txs.map { it.toDomain() },
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
