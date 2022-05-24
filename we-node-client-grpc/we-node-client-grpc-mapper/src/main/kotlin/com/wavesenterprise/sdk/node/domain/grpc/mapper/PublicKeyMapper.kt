package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.byteString

object PublicKeyMapper {
    @JvmStatic
    fun PublicKey.byteString(): ByteString =
        bytes.byteString()
}
