package com.wavesplatform.we.sdk.node.client.grpc.blocking.contract

import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc
import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc.ContractServiceBlockingStub
import com.wavesplatform.we.sdk.node.client.DataEntry
import com.wavesplatform.we.sdk.node.client.blocking.contract.ContractService
import com.wavesplatform.we.sdk.node.client.contract.ConnectionRequest
import com.wavesplatform.we.sdk.node.client.contract.ContractTransactionResponse
import com.wavesplatform.we.sdk.node.client.contract.ExecutionErrorRequest
import com.wavesplatform.we.sdk.node.client.contract.ExecutionSuccessRequest
import com.wavesplatform.we.sdk.node.client.contract.keys.ContractKeyRequest
import com.wavesplatform.we.sdk.node.client.contract.keys.ContractKeysRequest
import com.wavesplatform.we.sdk.node.client.grpc.mapper.DataEntryMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.contract.ConnectionRequestMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.contract.ContractKeysRequestMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.contract.ContractTransactionResponseMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.contract.ExecutionResultMapper.dto
import io.grpc.Channel

class ContractGrpcBlockingService(
    private val channel: Channel
) : ContractService {

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
