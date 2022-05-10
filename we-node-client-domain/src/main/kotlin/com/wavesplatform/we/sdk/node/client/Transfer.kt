package com.wavesplatform.we.sdk.node.client

data class Transfer(
    val recipient: Address,
    val amount: Amount,
)
