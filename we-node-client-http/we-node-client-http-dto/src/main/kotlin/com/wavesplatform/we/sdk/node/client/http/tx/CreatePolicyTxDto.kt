package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.PolicyDescription
import com.wavesplatform.we.sdk.node.client.PolicyName
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.tx.CreatePolicyTx

data class CreatePolicyTxDto(
    override val id: String,
    override val type: Int = TxType.CREATE_POLICY.code,
    val senderPublicKey: String,
    val policyName: String,
    val description: String,
    val recipients: List<String>,
    val owners: List<String>,
    override val timestamp: Long,
    val fee: Long,
    val feeAssetId: String?,
    val atomicBadge: AtomicBadgeDto?,
    val proofs: List<String>?,
    val sender: String,
    val version: Int,
) : TxDto, AtomicInnerTxDto, AtomicSignInnerTxDto {
    companion object {
        @JvmStatic
        fun CreatePolicyTx.toDto(): CreatePolicyTxDto =
            CreatePolicyTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                policyName = policyName.value,
                description = description.value,
                recipients = recipients.map { it.asBase58String() },
                owners = owners.map { it.asBase58String() },
                timestamp = timestamp.utcTimestampMillis,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                atomicBadge = atomicBadge?.toDto(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun CreatePolicyTxDto.toDomain(): CreatePolicyTx =
            CreatePolicyTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                policyName = PolicyName.fromString(policyName),
                description = PolicyDescription.fromString(description),
                recipients = recipients.map { Address.fromBase58(it) },
                owners = owners.map { Address.fromBase58(it) },
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                fee = Fee(fee),
                feeAssetId = feeAssetId?.let {
                    FeeAssetId.fromBase58(it)
                },
                atomicBadge = atomicBadge?.toDomain(),
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDtoInternal(tx: CreatePolicyTx): CreatePolicyTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: CreatePolicyTxDto): CreatePolicyTx =
            tx.toDomain()
    }
}
