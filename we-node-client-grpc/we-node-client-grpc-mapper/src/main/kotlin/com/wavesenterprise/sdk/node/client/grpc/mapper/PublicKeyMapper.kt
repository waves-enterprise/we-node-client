package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.sdk.node.domain.PublicKey

object PublicKeyMapper {
    @JvmStatic
    fun PublicKey.byteString(): ByteString =
        bytes.byteString()
}
