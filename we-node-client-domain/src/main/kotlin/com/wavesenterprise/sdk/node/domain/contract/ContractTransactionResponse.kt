package com.wavesenterprise.sdk.node.domain.contract

data class ContractTransactionResponse(
    val transaction: ContractTransaction,
    val authToken: AuthToken
)
