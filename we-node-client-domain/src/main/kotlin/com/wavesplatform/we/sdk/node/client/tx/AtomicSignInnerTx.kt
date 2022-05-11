package com.wavesplatform.we.sdk.node.client.tx

import com.wavesplatform.we.sdk.node.client.atomic.HasAtomicBadge

sealed interface AtomicSignInnerTx<T : Tx> : Tx, HasAtomicBadge<AtomicSignInnerTx<T>>
