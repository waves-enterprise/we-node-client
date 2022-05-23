package com.wavesplatform.we.sdk.node.client.grpc.mapper.contract

import com.wavesenterprise.protobuf.service.contract.executionErrorRequest
import com.wavesenterprise.protobuf.service.contract.executionSuccessRequest
import com.wavesplatform.we.sdk.node.client.contract.ExecutionErrorRequest
import com.wavesplatform.we.sdk.node.client.contract.ExecutionSuccessRequest
import com.wavesplatform.we.sdk.node.client.grpc.mapper.DataEntryMapper.dto
import com.wavesenterprise.protobuf.service.contract.ExecutionErrorRequest as ProtoExecutionErrorRequest
import com.wavesenterprise.protobuf.service.contract.ExecutionSuccessRequest as ProtoExecutionSuccessRequest

object ExecutionResultMapper {

    @JvmStatic
    fun ExecutionErrorRequest.dto() = dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(executionErrorRequest: ExecutionErrorRequest): ProtoExecutionErrorRequest =
        executionErrorRequest.let {
            executionErrorRequest {
                txId = it.txId.asBase58String()
                code = it.code
                message = it.message
            }
        }

    @JvmStatic
    fun ExecutionSuccessRequest.dto() = dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(executionSuccessRequest: ExecutionSuccessRequest): ProtoExecutionSuccessRequest =
        executionSuccessRequest.let {
            executionSuccessRequest {
                txId = it.txId.asBase58String()
                it.results.forEach { results.add(it.dto()) }
            }
        }
}
