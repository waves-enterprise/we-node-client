package com.wavesenterprise.sdk.node.domain

data class Transfer(
    val recipient: Address,
    val amount: Amount,
)
