package com.wavesenterprise.sdk.node.domain.atomic

interface HasMutableAtomicBadge<T : HasMutableAtomicBadge<T>> : HasAtomicBadge {
    fun withAtomicBadge(atomicBadge: AtomicBadge?): T
}
