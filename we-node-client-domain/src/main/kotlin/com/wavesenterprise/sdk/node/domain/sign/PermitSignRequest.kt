package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.PermitTx

data class PermitSignRequest(
    val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val target: Address,
    val opType: OpType,
    val dueTimestamp: Timestamp,
    val atomicBadge: AtomicBadge? = null,
) : SignRequest<PermitTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password) = copy(password = password)
}
