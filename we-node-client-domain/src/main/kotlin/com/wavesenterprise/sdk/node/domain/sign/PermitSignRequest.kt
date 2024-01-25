package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PermissionOp
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Role
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.PermitTx

data class PermitSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val target: Address,
    val role: Role,
    val opType: OpType,
    val dueTimestamp: Timestamp,
    override val atomicBadge: AtomicBadge? = null,
) : AtomicInnerSignRequest<PermitTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?): SignRequest<PermitTx> = copy(password = password)
    override fun withAtomicBadge(atomicBadge: AtomicBadge?) =
        copy(atomicBadge = atomicBadge)

    companion object {
        @JvmStatic
        fun PermitSignRequest.toTx(senderPublicKey: PublicKey) = PermitTx(
            id = TxId.EMPTY,
            senderPublicKey = senderPublicKey,
            target = target,
            timestamp = Timestamp(System.currentTimeMillis()),
            fee = fee,
            permissionOp = PermissionOp(
                opType = opType,
                role = role,
                timestamp = Timestamp(System.currentTimeMillis()),
                dueTimestamp = dueTimestamp,
            ),
            atomicBadge = atomicBadge,
            proofs = listOf(),
            senderAddress = senderAddress,
            version = requireNotNull(version),
        )
    }
}
