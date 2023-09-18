package com.wavesenterprise.sdk.node.domain.tx

import com.wavesenterprise.sdk.node.domain.atomic.HasAtomicBadge

sealed interface AtomicInnerTx : Tx, HasAtomicBadge
