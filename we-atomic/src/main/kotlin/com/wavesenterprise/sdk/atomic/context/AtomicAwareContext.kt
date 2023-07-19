package com.wavesenterprise.sdk.atomic.context

import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx

interface AtomicAwareContext {
    fun isSentInAtomic(): Boolean
    fun addTx(tx: Tx)
    fun commitAtomic(): AtomicSignRequest
}
