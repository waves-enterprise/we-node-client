package com.wavesplatform.we.sdk.node.client.blocking.contract

import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.contract.ConnectionRequest
import com.wavesplatform.we.sdk.node.client.contract.ContractTransactionResponse
import com.wavesplatform.we.sdk.node.client.contract.ExecutionErrorRequest
import com.wavesplatform.we.sdk.node.client.contract.ExecutionSuccessRequest
import com.wavesplatform.we.sdk.node.client.contract.keys.ContractKeyRequest
import com.wavesplatform.we.sdk.node.client.contract.keys.ContractKeysRequest

interface ContractService {
    fun connect(connectionRequest: ConnectionRequest): Sequence<ContractTransactionResponse>
    fun commitExecutionSuccess(executionSuccessRequest: ExecutionSuccessRequest)
    fun commitExecutionError(executionErrorRequest: ExecutionErrorRequest)
    fun getContractKeys(contractKeysRequest: ContractKeysRequest): List<DataEntry>
    fun getContractKey(contractKeyRequest: ContractKeyRequest): DataEntry
}
