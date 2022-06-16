package com.wavesenterprise.sdk.node.domain.http.tx

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ChainId
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.LeaseId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.LeaseCancelTx

data class LeaseCancelTxDto(
    override val id: String,
    override val type: Int = TxType.LEASE_CANCEL.code,
    val chainId: Byte,
    val senderPublicKey: String,
    val fee: Long,
    override val timestamp: Long,
    val leaseId: String,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto {
    companion object {
        @JvmStatic
        fun LeaseCancelTx.toDto(): LeaseCancelTxDto =
            LeaseCancelTxDto(
                id = id.asBase58String(),
                chainId = chainId.value,
                senderPublicKey = senderPublicKey.asBase58String(),
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                leaseId = leaseId.asBase58String(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun LeaseCancelTxDto.toDomain(): LeaseCancelTx =
            LeaseCancelTx(
                id = TxId.fromBase58(id),
                chainId = ChainId(chainId),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                leaseId = LeaseId.fromBase58(leaseId),
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDomainInternal(tx: LeaseCancelTxDto): LeaseCancelTx =
            tx.toDomain()
    }
}
