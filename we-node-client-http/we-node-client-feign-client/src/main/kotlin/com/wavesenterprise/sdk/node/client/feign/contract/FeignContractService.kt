package com.wavesenterprise.sdk.node.client.feign.contract

import com.wavesenterprise.sdk.node.client.blocking.contract.ContractService
import com.wavesenterprise.sdk.node.client.http.DataEntryDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.contract.ContractInfoDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.contract.ContractTxStatusDto.Companion.toDomain
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
import com.wavesenterprise.sdk.node.exception.specific.ContractNotFoundException
import com.wavesenterprise.sdk.node.exception.specific.DataKeyNotExistsException
import java.util.Optional

class FeignContractService(
    private val weContractServiceApiFeign: WeContractServiceApiFeign,
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

    override fun getContractKeys(contractKeysRequest: ContractKeysRequest): List<DataEntry> =
        weContractServiceApiFeign.contractKeys(
            contractId = contractKeysRequest.contractId.asBase58String(),
            limit = contractKeysRequest.limit,
            offset = contractKeysRequest.offset,
            matches = contractKeysRequest.matches,
        ).map { it.toDomain() }

    override fun getContractKey(contractKeyRequest: ContractKeyRequest): Optional<DataEntry> =
        try {
            weContractServiceApiFeign.contractKey(
                contractId = contractKeyRequest.contractId.asBase58String(),
                key = contractKeyRequest.key,
            ).map { it.toDomain() }
        } catch (ex: DataKeyNotExistsException) {
            Optional.empty()
        }

    override fun getContractInfo(contractId: ContractId): Optional<ContractInfo> =
        try {
            Optional.of(weContractServiceApiFeign.contractInfo(contractId.asBase58String()).toDomain())
        } catch (ex: ContractNotFoundException) {
            Optional.empty()
        }

    override fun getContractTxStatus(txId: TxId): List<ContractTxStatus> =
        weContractServiceApiFeign.contractTxStatus(txId.asBase58String()).map { it.toDomain() }
}
