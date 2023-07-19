package com.wavesenterprise.sdk.atomic.manager

import com.wavesenterprise.sdk.atomic.context.AtomicAwareContext
import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest

interface AtomicAwareContextManager {
    fun getContext(): AtomicAwareContext

    fun beginAtomic()

    fun commitAtomic(): AtomicSignRequest

    fun clearContext()
}
