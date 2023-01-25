package com.wavesenterprise.sdk.node.client.grpc.blocking.util

import com.wavesenterprise.sdk.node.client.grpc.blocking.GrpcNodeErrorMapper
import io.grpc.StatusRuntimeException

inline fun <T> catchingNodeCall(nodeCall: () -> T): T =
    try {
        nodeCall()
    } catch (ex: StatusRuntimeException) {
        throw GrpcNodeErrorMapper.mapToGeneralException(ex)
    }
