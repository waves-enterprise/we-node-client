package com.wavesplatform.we.sdk.node.client.grpc.mapper.contract

import com.wavesenterprise.protobuf.service.contract.contractKeyRequest
import com.wavesenterprise.protobuf.service.contract.contractKeysRequest
import com.wavesenterprise.protobuf.service.contract.keysFilter
import com.wavesplatform.we.sdk.node.client.contract.keys.ContractKeyRequest
import com.wavesplatform.we.sdk.node.client.contract.keys.ContractKeysRequest
import com.wavesenterprise.protobuf.service.contract.ContractKeyRequest as ProtoContractKeyRequest
import com.wavesenterprise.protobuf.service.contract.ContractKeysRequest as ProtoContractKeysRequest

object ContractKeysRequestMapper {

    @JvmStatic
    fun ContractKeyRequest.dto(): ProtoContractKeyRequest =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(contractKeyRequest: ContractKeyRequest): ProtoContractKeyRequest =
        contractKeyRequest.let {
            contractKeyRequest {
                contractId = it.contractId.asBase58String()
                key = it.key
            }
        }

    @JvmStatic
    fun ContractKeysRequest.dto(): ProtoContractKeysRequest = dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(contractKeyRequest: ContractKeysRequest): ProtoContractKeysRequest =
        contractKeyRequest.let {
            contractKeysRequest {
                contractId = it.contractId.asBase58String()
                keysFilter = keysFilter {
                    it.keysFilter.keys.forEach { key -> keys.add(key) }
                }
            }
        }
}
