package com.wavesplatform.we.sdk.node.client.contract

data class ContractTransactionResponse(
    val transaction: ContractTransaction,
    val authToken: String
)
