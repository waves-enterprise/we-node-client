package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.domain.Signature
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.byteString

object SignatureMapper {
    @JvmStatic
    fun Signature.byteString(): ByteString =
        bytes.byteString()
}
