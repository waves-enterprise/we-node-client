package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx

data class PolicyDataHashTxDto(
    override val id: String,
    override val type: Int = TxType.POLICY_DATA_HASH.code,
    val senderPublicKey: String,
    val dataHash: String,
    val policyId: String,
    override val timestamp: Long,
    val fee: Long,
    val feeAssetId: String?,
    val atomicBadge: AtomicBadgeDto?,
    val proofs: List<String>?,
    val sender: String,
) : TxDto, AtomicInnerTxDto, AtomicSignInnerTxDto {
    companion object {
        @JvmStatic
        fun PolicyDataHashTx.toDto(): PolicyDataHashTxDto =
            PolicyDataHashTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                dataHash = dataHash.asHexString(),
                policyId = policyId.asBase58String(),
                timestamp = timestamp.utcTimestampMillis,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                atomicBadge = atomicBadge?.toDto(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
            )

        @JvmStatic
        fun PolicyDataHashTxDto.toDomain(): PolicyDataHashTx =
            PolicyDataHashTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                dataHash = Hash.fromHexString(dataHash),
                policyId = PolicyId.fromBase58(policyId),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                fee = Fee(fee),
                feeAssetId = feeAssetId?.let { FeeAssetId.fromBase58(it) },
                atomicBadge = atomicBadge?.toDomain(),
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
            )

        internal fun toDtoInternal(tx: PolicyDataHashTx): PolicyDataHashTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: PolicyDataHashTxDto): PolicyDataHashTx =
            tx.toDomain()
    }
}
