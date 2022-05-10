package com.wavesplatform.we.sdk.node.client.event

import com.wavesplatform.we.sdk.node.client.Signature

sealed interface StartFrom {
    object Genesis : StartFrom
    data class BlockSignature(val signature: Signature) : StartFrom
    object Current : StartFrom
}
