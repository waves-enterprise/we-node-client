package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.TransferTx

data class TransferSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val recipient: Address,
    val amount: Amount,
    override val atomicBadge: AtomicBadge? = null,
) : AtomicInnerSignRequest<TransferTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?): SignRequest<TransferTx> = copy(password = password)
    override fun withAtomicBadge(atomicBadge: AtomicBadge?) =
        copy(atomicBadge = atomicBadge)
}
