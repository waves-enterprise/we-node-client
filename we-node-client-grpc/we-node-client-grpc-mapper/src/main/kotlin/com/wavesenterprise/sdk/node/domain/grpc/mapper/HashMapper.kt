package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.byteString

object HashMapper {
    @JvmStatic
    fun Hash.byteString(): ByteString =
        bytes.byteString()
}
