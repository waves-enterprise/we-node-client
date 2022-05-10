package com.wavesplatform.we.sdk.node.client.atomic

import com.wavesplatform.we.sdk.node.client.AtomicBadge

interface HasAtomicBadge<T : HasAtomicBadge<T>> {
    val atomicBadge: AtomicBadge?

    fun withAtomicBadge(atomicBadge: AtomicBadge?): T
}
