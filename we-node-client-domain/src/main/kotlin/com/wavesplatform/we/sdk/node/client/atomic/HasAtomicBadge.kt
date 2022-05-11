package com.wavesplatform.we.sdk.node.client.atomic

interface HasAtomicBadge<T : HasAtomicBadge<T>> {
    val atomicBadge: AtomicBadge?

    fun withAtomicBadge(atomicBadge: AtomicBadge?): T
}
