package com.wavesenterprise.sdk.node.domain.event

import com.wavesenterprise.sdk.node.domain.Signature

sealed interface StartFrom {
    object Genesis : StartFrom
    data class BlockSignature(val signature: Signature) : StartFrom
    object Current : StartFrom
}
