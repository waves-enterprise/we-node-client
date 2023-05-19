package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest

class ThreadLocalAtomicAwareContextManager : AtomicAwareContextManager {
    override fun getContext(): AtomicAwareContext = contextHolder.get()

    override fun beginAtomic() {
        contextHolder.set(DefaultAtomicAwareContext())
    }

    override fun commitAtomic(): AtomicSignRequest =
        try {
            getContext().commitAtomic()
        } finally {
            clearContext()
        }

    override fun clearContext() {
        contextHolder.set(EmptyAtomicAwareContext())
    }

    companion object {
        private val contextHolder: ThreadLocal<AtomicAwareContext> =
            ThreadLocal.withInitial { EmptyAtomicAwareContext() }
    }
}
