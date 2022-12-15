package com.wavesenterprise.sdk.node.domain.grpc.blocking.util

import com.wavesenterprise.sdk.node.domain.grpc.blocking.GrpcNodeErrorMapper
import io.grpc.StatusRuntimeException

inline fun <T> catchingNodeCall(nodeCall: () -> T): T =
    try {
        nodeCall()
    } catch (ex: StatusRuntimeException) {
        throw GrpcNodeErrorMapper.mapToGeneralException(ex)
    }
