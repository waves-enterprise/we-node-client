package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.tx.AtomicInnerTx
import com.wavesenterprise.sdk.node.domain.tx.Tx
import java.lang.IllegalArgumentException

class DefaultAtomicAwareContext : AtomicAwareContext {

    private val txs: MutableMap<TxId, AtomicInnerTx> = mutableMapOf()

    private var atomicFlag: Boolean = true

    override fun isSentInAtomic() = atomicFlag

    override fun addTx(tx: Tx) {
        when (tx) {
            is AtomicInnerTx -> txs += tx.id to tx
            else -> throw IllegalArgumentException("Tx with type ${tx.javaClass} is not of type AtomicInnerTx")
        }
    }

    override fun commitAtomic(): AtomicSignRequest =
        AtomicSignRequest(
            version = TxVersion(2),
            senderAddress = Address(ByteArray(0)),
            fee = Fee(0),
            txs = txs.values.toList(),
        ).also {
            atomicFlag = false
        }
}
