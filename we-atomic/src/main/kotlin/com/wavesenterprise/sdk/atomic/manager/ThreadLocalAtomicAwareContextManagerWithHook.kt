package com.wavesenterprise.sdk.atomic.manager

import com.wavesenterprise.sdk.atomic.context.AtomicAwareContext
import com.wavesenterprise.sdk.atomic.context.DefaultAtomicAwareContext
import com.wavesenterprise.sdk.atomic.context.EmptyAtomicAwareContext
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest

class ThreadLocalAtomicAwareContextManagerWithHook(
    private val atomicAwareContextManagerHook: AtomicAwareContextManagerHook
) : AtomicAwareContextManager {
    override fun getContext(): AtomicAwareContext = contextHolder.get()

    override fun beginAtomic() {
        contextHolder.set(DefaultAtomicAwareContext())
        atomicAwareContextManagerHook.begin()
    }

    override fun commitAtomic(): AtomicSignRequest =
        try {
            getContext().commitAtomic()
        } finally {
            clearContext()
        }

    override fun clearContext() {
        contextHolder.set(EmptyAtomicAwareContext())
        atomicAwareContextManagerHook.clear()
    }

    companion object {
        private val contextHolder: ThreadLocal<AtomicAwareContext> =
            ThreadLocal.withInitial { EmptyAtomicAwareContext() }
    }
}
