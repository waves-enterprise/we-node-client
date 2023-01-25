package com.wavesenterprise.sdk.node.client.grpc.mapper.contract

import com.wavesenterprise.protobuf.service.contract.connectionRequest
import com.wavesenterprise.sdk.node.domain.contract.ConnectionRequest
import com.wavesenterprise.protobuf.service.contract.ConnectionRequest as ProtoConnectionRequest

object ConnectionRequestMapper {
    @JvmStatic
    fun ConnectionRequest.dto(): ProtoConnectionRequest =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(connectionRequest: ConnectionRequest): ProtoConnectionRequest =
        connectionRequest {
            connectionId = connectionRequest.connectionId
        }
}
