package com.wavesplatform.we.sdk.node.client.http.tx

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Proof
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.AtomicBadgeDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.PermissionOpDto
import com.wavesplatform.we.sdk.node.client.http.PermissionOpDto.Companion.toDomain
import com.wavesplatform.we.sdk.node.client.http.PermissionOpDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.AtomicInnerTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.tx.PermitTx

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
    val version: Int,
) : TxDto, AtomicInnerTxDto {
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
                proofs = proofs?.map { Proof.fromBase58(it) },
                senderAddress = Address.fromBase58(sender),
                version = TxVersion(version),
            )

        internal fun toDtoInternal(tx: PermitTx): PermitTxDto =
            tx.toDto()

        internal fun toDomainInternal(tx: PermitTxDto): PermitTx =
            tx.toDomain()
    }
}
