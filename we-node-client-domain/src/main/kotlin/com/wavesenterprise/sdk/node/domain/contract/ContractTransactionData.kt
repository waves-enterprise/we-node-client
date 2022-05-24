package com.wavesenterprise.sdk.node.domain.contract

sealed interface ContractTransactionData

data class CreateContractTransactionData(
    val image: String,
    val imageHash: String,
    val contractName: String
) : ContractTransactionData

data class CallContractTransactionData(
    val contractVersion: Int
) : ContractTransactionData
