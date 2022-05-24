package com.wavesenterprise.sdk.node.domain.atomic

interface HasAtomicBadge<T : HasAtomicBadge<T>> {
    val atomicBadge: AtomicBadge?

    fun withAtomicBadge(atomicBadge: AtomicBadge?): T
}
