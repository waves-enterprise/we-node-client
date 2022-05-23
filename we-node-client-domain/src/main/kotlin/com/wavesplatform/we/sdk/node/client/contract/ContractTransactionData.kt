package com.wavesplatform.we.sdk.node.client.contract

sealed interface ContractTransactionData

data class CreateContractTransactionData(
    val image: String,
    val imageHash: String,
    val contractName: String
) : ContractTransactionData

data class CallContractTransactionData(
    val contractVersion: Int
) : ContractTransactionData
