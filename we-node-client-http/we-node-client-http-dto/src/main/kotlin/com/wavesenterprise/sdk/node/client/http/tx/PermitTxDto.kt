package com.wavesenterprise.sdk.node.client.http.tx

import com.wavesenterprise.sdk.node.client.http.PermissionOpDto
import com.wavesenterprise.sdk.node.client.http.PermissionOpDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.PermissionOpDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.atomic.AtomicBadgeDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.PermitTx

data class PermitTxDto(
    override val id: String,
    override val type: Int = TxType.PERMIT.code,
    val senderPublicKey: String,
    val target: String,
    override val timestamp: Long,
    val fee: Long,
    val permissionOp: PermissionOpDto,
    val atomicBadge: AtomicBadgeDto?,
    val proofs: List<String>?,
    val sender: String,
    override val version: Int,
    override val height: Long? = null,
) : TxDto, AtomicInnerTxDto, AtomicSignInnerTxDto {
    companion object {
        @JvmStatic
        fun PermitTx.toDto(): PermitTxDto =
            PermitTxDto(
                id = id.asBase58String(),
                senderPublicKey = senderPublicKey.asBase58String(),
                target = target.asBase58String(),
                timestamp = timestamp.utcTimestampMillis,
                fee = fee.value,
                permissionOp = permissionOp.toDto(),
                atomicBadge = atomicBadge?.toDto(),
                proofs = proofs?.map { it.asBase58String() },
                sender = senderAddress.asBase58String(),
                version = version.value,
            )

        @JvmStatic
        fun PermitTxDto.toDomain(): PermitTx =
            PermitTx(
                id = TxId.fromBase58(id),
                senderPublicKey = PublicKey.fromBase58(senderPublicKey),
                target = Address.fromBase58(target),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                fee = Fee(fee),
                permissionOp = permissionOp.toDomain(),
                atomicBadge = atomicBadge?.toDomain(),
                proofs = proofs?.map { Signature.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDtoInternal(tx: PermitTx): PermitTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: PermitTxDto): PermitTx =
            tx.toDomain()
    }
}
