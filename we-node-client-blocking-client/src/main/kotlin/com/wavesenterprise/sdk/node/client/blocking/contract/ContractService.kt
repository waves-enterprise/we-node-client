package com.wavesenterprise.sdk.node.client.blocking.contract

import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.contract.ConnectionRequest
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractInfo
import com.wavesenterprise.sdk.node.domain.contract.ContractTransactionResponse
import com.wavesenterprise.sdk.node.domain.contract.ExecutionErrorRequest
import com.wavesenterprise.sdk.node.domain.contract.ExecutionSuccessRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeyRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeysRequest
import java.util.Optional

interface ContractService {
    fun connect(connectionRequest: ConnectionRequest): Sequence<ContractTransactionResponse>
    fun commitExecutionSuccess(executionSuccessRequest: ExecutionSuccessRequest)
    fun commitExecutionError(executionErrorRequest: ExecutionErrorRequest)
    fun getContractKeys(contractKeysRequest: ContractKeysRequest): List<DataEntry>
    fun getContractKey(contractKeyRequest: ContractKeyRequest): Optional<DataEntry>

    fun getContractInfo(contractId: ContractId): Optional<ContractInfo>
}
