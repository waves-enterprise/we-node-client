package com.wavesenterprise.sdk.node.client.feign.contract

import com.wavesenterprise.sdk.node.domain.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.domain.contract.ConnectionRequest
import com.wavesenterprise.sdk.node.domain.contract.ContractTransactionResponse
import com.wavesenterprise.sdk.node.domain.contract.ExecutionErrorRequest
import com.wavesenterprise.sdk.node.domain.contract.ExecutionSuccessRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeyRequest
import com.wavesenterprise.sdk.node.domain.contract.keys.ContractKeysRequest
import com.wavesenterprise.sdk.node.domain.http.DataEntryDto.Companion.toDomain

class FeignContractService(
    private val weContractServiceApiFeign: WeContractServiceApiFeign
) : ContractService {
    override fun connect(connectionRequest: ConnectionRequest): Sequence<ContractTransactionResponse> {
        TODO("Not yet implemented")
    }

    override fun commitExecutionSuccess(executionSuccessRequest: ExecutionSuccessRequest) {
        TODO("Not yet implemented")
    }

    override fun commitExecutionError(executionErrorRequest: ExecutionErrorRequest) {
        TODO("Not yet implemented")
    }

    override fun getContractKeys(contractKeysRequest: ContractKeysRequest) =
        weContractServiceApiFeign.contractKeys(
            contractId = contractKeysRequest.contractId.asBase58String(),
            limit = contractKeysRequest.limit,
            offset = contractKeysRequest.offset,
            matches = contractKeysRequest.matches,
        ).map { it.toDomain() }

    override fun getContractKey(contractKeyRequest: ContractKeyRequest) =
        weContractServiceApiFeign.contractKey(
            contractId = contractKeyRequest.contractId.asBase58String(),
            key = contractKeyRequest.key,
        ).map { it.toDomain() }
}
