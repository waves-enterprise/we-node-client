package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.AtomicInnerTx
import com.wavesenterprise.sdk.node.domain.tx.AtomicTx

data class AtomicSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val txs: List<AtomicInnerTx>,
    val fee: Fee,
) : SignRequest<AtomicTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?) = copy(password = password)

    companion object {
        @JvmStatic
        fun AtomicSignRequest.toTx(senderPublicKey: PublicKey) = AtomicTx(
            id = TxId.EMPTY,
            senderPublicKey = senderPublicKey,
            timestamp = Timestamp(System.currentTimeMillis()),
            txs = txs,
            proofs = listOf(),
            senderAddress = senderAddress,
            fee = fee,
            version = requireNotNull(version),
        )
    }
}
