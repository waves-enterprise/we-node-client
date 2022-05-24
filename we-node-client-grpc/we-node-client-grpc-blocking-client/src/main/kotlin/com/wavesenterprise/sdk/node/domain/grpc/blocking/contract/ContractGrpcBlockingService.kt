package com.wavesenterprise.sdk.node.domain.grpc.blocking.contract

import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc
import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc.ContractServiceBlockingStub
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.domain.contract.ConnectionRequest
import com.wavesenterprise.sdk.node.domain.contract.ContractTransactionResponse
import com.wavesenterprise.sdk.node.domain.contract.ExecutionErrorRequest
import com.wavesenterprise.sdk.node.domain.contract.ExecutionSuccessRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeyRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeysRequest
import com.wavesenterprise.sdk.node.domain.grpc.mapper.DataEntryMapper.domain
import com.wavesenterprise.sdk.node.domain.grpc.mapper.contract.ConnectionRequestMapper.dto
import com.wavesenterprise.sdk.node.domain.grpc.mapper.contract.ContractKeysRequestMapper.dto
import com.wavesenterprise.sdk.node.domain.grpc.mapper.contract.ContractTransactionResponseMapper.domain
import com.wavesenterprise.sdk.node.domain.grpc.mapper.contract.ExecutionResultMapper.dto
import io.grpc.Channel

class ContractGrpcBlockingService(
    private val channel: Channel
) : com.wavesenterprise.sdk.node.domain.blocking.contract.ContractService {

    private val contractServiceStub: ContractServiceBlockingStub = ContractServiceGrpc.newBlockingStub(channel)

    override fun connect(connectionRequest: ConnectionRequest): Sequence<ContractTransactionResponse> =
        contractServiceStub.connect(connectionRequest.dto())
            .asSequence()
            .map { it.domain() }

    override fun commitExecutionSuccess(executionSuccessRequest: ExecutionSuccessRequest) {
        contractServiceStub.commitExecutionSuccess(executionSuccessRequest.dto())
    }

    override fun commitExecutionError(executionErrorRequest: ExecutionErrorRequest) {
        contractServiceStub.commitExecutionError(executionErrorRequest.dto())
    }

    override fun getContractKeys(contractKeysRequest: ContractKeysRequest): List<DataEntry> =
        contractServiceStub.getContractKeys(contractKeysRequest.dto()).entriesList.map { it.domain() }

    override fun getContractKey(contractKeyRequest: ContractKeyRequest): DataEntry =
        contractServiceStub.getContractKey(contractKeyRequest.dto()).entry.domain()
}
