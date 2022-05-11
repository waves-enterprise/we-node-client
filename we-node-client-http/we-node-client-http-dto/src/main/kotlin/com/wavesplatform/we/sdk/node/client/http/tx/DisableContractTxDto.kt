package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.ContractId
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.tx.DisableContractTx

data class DisableContractTxDto(
    override val id: String,
    override val type: Int = TxType.DISABLE_CONTRACT.code,
    val senderPublicKey: String,
    val contractId: String,
    val fee: Long,
    override val timestamp: Long,
    val feeAssetId: String?,
    val atomicBadge: AtomicBadgeDto?,
    val proofs: List<String>?,
    val sender: String,
    val version: Int,
) : TxDto, AtomicInnerTxDto, AtomicSignInnerTxDto {
    companion object {
        @JvmStatic
        fun DisableContractTx.toDto(): DisableContractTxDto =
            DisableContractTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                contractId = contractId.asBase58String(),
                fee = fee.value,
                timestamp = timestamp.utcTimestampMillis,
                feeAssetId = feeAssetId?.asBase58String(),
                atomicBadge = atomicBadge?.toDto(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun DisableContractTxDto.toDomain(): DisableContractTx =
            DisableContractTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                contractId = ContractId.fromBase58(contractId),
                fee = Fee(fee),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                feeAssetId = feeAssetId?.let { FeeAssetId.fromBase58(it) },
                atomicBadge = atomicBadge?.toDomain(),
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDtoInternal(tx: DisableContractTx): DisableContractTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: DisableContractTxDto): DisableContractTx =
            tx.toDomain()
    }
}
