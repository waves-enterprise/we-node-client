package com.wavesplatform.we.sdk.node.client.grpc.mapper.contract

import com.wavesenterprise.protobuf.service.contract.connectionRequest
import com.wavesplatform.we.sdk.node.client.contract.ConnectionRequest
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
