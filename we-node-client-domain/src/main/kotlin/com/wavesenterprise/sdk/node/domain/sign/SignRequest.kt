package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.tx.Tx

sealed interface SignRequest<T : Tx>
