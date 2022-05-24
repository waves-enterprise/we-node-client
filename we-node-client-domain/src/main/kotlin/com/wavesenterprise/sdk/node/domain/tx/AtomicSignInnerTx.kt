package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.atomic.HasAtomicBadge

sealed interface AtomicSignInnerTx<T : Tx> : Tx, HasAtomicBadge<AtomicSignInnerTx<T>>
