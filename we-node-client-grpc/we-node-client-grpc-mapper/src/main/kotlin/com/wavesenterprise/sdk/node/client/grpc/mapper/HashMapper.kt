package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.sdk.node.domain.Hash

object HashMapper {
    @JvmStatic
    fun Hash.byteString(): ByteString =
        bytes.byteString()
}
