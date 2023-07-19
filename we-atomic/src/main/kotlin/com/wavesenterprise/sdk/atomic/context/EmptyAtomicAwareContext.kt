package com.wavesenterprise.sdk.atomic.context

import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest
import com.wavesenterprise.sdk.node.domain.tx.Tx
import java.lang.UnsupportedOperationException

class EmptyAtomicAwareContext : AtomicAwareContext {
    override fun isSentInAtomic() = false

    override fun addTx(tx: Tx) {
        throw UnsupportedOperationException()
    }

    override fun commitAtomic(): AtomicSignRequest {
        throw UnsupportedOperationException()
    }
}
