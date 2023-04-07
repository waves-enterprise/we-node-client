package com.wavesenterprise.sdk.node.client.grpc.blocking.contract

import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc
import com.wavesenterprise.protobuf.service.contract.ContractServiceGrpc.ContractServiceBlockingStub
import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.grpc.blocking.GrpcNodeErrorMapper
import com.wavesenterprise.sdk.node.client.grpc.blocking.util.catchingNodeCall
import com.wavesenterprise.sdk.node.client.grpc.mapper.DataEntryMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.contract.ConnectionRequestMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.contract.ContractKeysRequestMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.contract.ContractTransactionResponseMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.contract.ExecutionResultMapper.dto
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.contract.ConnectionRequest
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractInfo
import com.wavesenterprise.sdk.node.domain.contract.ContractTransactionResponse
import com.wavesenterprise.sdk.node.domain.contract.ContractTxStatus
import com.wavesenterprise.sdk.node.domain.contract.ExecutionErrorRequest
import com.wavesenterprise.sdk.node.domain.contract.ExecutionSuccessRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeyRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeysRequest
import com.wavesenterprise.sdk.node.exception.specific.DataKeyNotExistsException
import io.grpc.Channel
import io.grpc.ClientInterceptor
import io.grpc.Status
import io.grpc.StatusRuntimeException
import java.util.Optional

class ContractGrpcBlockingService(
    private val channel: Channel,
    private val clientInterceptors: List<ClientInterceptor> = emptyList(),
    private val contractServiceStub: ContractServiceBlockingStub =
        ContractServiceGrpc.newBlockingStub(channel).withInterceptors(*clientInterceptors.toTypedArray()),
) : ContractService {

    override fun connect(connectionRequest: ConnectionRequest): Sequence<ContractTransactionResponse> =
        catchingNodeCall {
            contractServiceStub.connect(connectionRequest.dto())
                .asSequence()
                .map { it.domain() }
        }

    override fun commitExecutionSuccess(executionSuccessRequest: ExecutionSuccessRequest) {
        catchingNodeCall {
            contractServiceStub.commitExecutionSuccess(executionSuccessRequest.dto())
        }
    }

    override fun commitExecutionError(executionErrorRequest: ExecutionErrorRequest) {
        catchingNodeCall {
            contractServiceStub.commitExecutionError(executionErrorRequest.dto())
        }
    }

    override fun getContractKeys(contractKeysRequest: ContractKeysRequest): List<DataEntry> =
        catchingNodeCall {
            contractServiceStub.getContractKeys(contractKeysRequest.dto()).entriesList.map { it.domain() }
        }

    override fun getContractKey(contractKeyRequest: ContractKeyRequest): Optional<DataEntry> =
        Optional.ofNullable(
            try {
                contractServiceStub.getContractKey(contractKeyRequest.dto())?.entry?.domain()
            } catch (ex: StatusRuntimeException) {
                if (ex.status == Status.NOT_FOUND && ex.trailers == null)
                    null
                else {
                    when (val mappedException = GrpcNodeErrorMapper.mapToGeneralException(ex)) {
                        is DataKeyNotExistsException -> null
                        else -> throw mappedException
                    }
                }
            }
        )

    override fun getContractInfo(contractId: ContractId): Optional<ContractInfo> {
        throw IllegalArgumentException("Not implemented in grpc")
    }

    override fun getContractTxStatus(txId: TxId): List<ContractTxStatus> {
        throw IllegalArgumentException("Not implemented in grpc")
    }
}
