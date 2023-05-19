package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.atomic.HasMutableAtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.Tx

sealed interface AtomicInnerSignRequest<T : Tx> : SignRequest<T>, HasMutableAtomicBadge<AtomicInnerSignRequest<T>>
