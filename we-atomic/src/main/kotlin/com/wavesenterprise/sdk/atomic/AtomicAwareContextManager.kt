package com.wavesenterprise.sdk.atomic

import com.wavesenterprise.sdk.node.domain.sign.AtomicSignRequest

interface AtomicAwareContextManager {
    fun getContext(): AtomicAwareContext

    fun beginAtomic()

    fun commitAtomic(): AtomicSignRequest

    fun clearContext()
}
