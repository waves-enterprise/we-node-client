package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.bytesValue
import com.wavesenterprise.sdk.node.domain.Signature

object SignatureMapper {
    @JvmStatic
    fun Signature.byteString(): ByteString =
        bytes.byteString()

    @JvmStatic
    fun Signature.bytesValue(): BytesValue =
        bytes.bytesValue()
}
