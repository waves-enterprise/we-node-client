package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.ChainId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.LeaseId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.LeaseCancelTx

data class LeaseCancelTxDto(
    override val id: String,
    override val type: Int = TxType.LEASE_CANCEL.code,
    val chainId: Byte,
    val senderPublicKey: String,
    val fee: Long,
    override val timestamp: Long,
    val leaseId: String,
    val proofs: List<String>? = null,
    val sender: String,
    val version: Int,
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
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )
    }
}
