package com.wavesenterprise.sdk.node.domain.blocking.contract

import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.contract.ConnectionRequest
import com.wavesenterprise.sdk.node.domain.contract.ContractTransactionResponse
import com.wavesenterprise.sdk.node.domain.contract.ExecutionErrorRequest
import com.wavesenterprise.sdk.node.domain.contract.ExecutionSuccessRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeyRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeysRequest

interface ContractService {
    fun connect(connectionRequest: ConnectionRequest): Sequence<ContractTransactionResponse>
    fun commitExecutionSuccess(executionSuccessRequest: ExecutionSuccessRequest)
    fun commitExecutionError(executionErrorRequest: ExecutionErrorRequest)
    fun getContractKeys(contractKeysRequest: ContractKeysRequest): List<DataEntry>
    fun getContractKey(contractKeyRequest: ContractKeyRequest): DataEntry
}
