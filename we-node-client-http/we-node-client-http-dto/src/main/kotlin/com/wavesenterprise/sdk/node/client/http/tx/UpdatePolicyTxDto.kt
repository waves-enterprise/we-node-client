package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.client.http.OpTypeConstants.fromOpTypeDtoToDomain
import com.wavesenterprise.sdk.node.client.http.OpTypeConstants.toDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx

data class UpdatePolicyTxDto(
    override val id: String,
    override val type: Int = TxType.UPDATE_POLICY.code,
    val senderPublicKey: String,
    val policyId: String,
    val recipients: List<String>,
    val owners: List<String>,
    val opType: String,
    override val timestamp: Long,
    val fee: Long,
    val feeAssetId: String?,
    val atomicBadge: AtomicBadgeDto?,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto, AtomicInnerTxDto, AtomicSignInnerTxDto {
    companion object {
        @JvmStatic
        fun UpdatePolicyTx.toDto(): UpdatePolicyTxDto =
            UpdatePolicyTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                policyId = policyId.asBase58String(),
                recipients = recipients.map { it.asBase58String() },
                owners = owners.map { it.asBase58String() },
                opType = opType.toDto(),
                timestamp = timestamp.utcTimestampMillis,
                fee = fee.value,
                feeAssetId = feeAssetId?.asBase58String(),
                atomicBadge = atomicBadge?.toDto(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun UpdatePolicyTxDto.toDomain(): UpdatePolicyTx =
            UpdatePolicyTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                policyId = PolicyId.fromBase58(policyId),
                recipients = recipients.map { Address.fromBase58(it) },
                owners = owners.map { Address.fromBase58(it) },
                opType = opType.fromOpTypeDtoToDomain(),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                fee = Fee(fee),
                feeAssetId = feeAssetId?.let { FeeAssetId.fromBase58(it) },
                atomicBadge = atomicBadge?.toDomain(),
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDtoInternal(tx: UpdatePolicyTx): UpdatePolicyTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: UpdatePolicyTxDto): UpdatePolicyTx =
            tx.toDomain()
    }
}
