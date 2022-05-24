package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.domain.AssetId
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.bytesValue

object AssetIdMapper {
    @JvmStatic
    fun AssetId.byteString(): ByteString =
        bytes.byteString()

    @JvmStatic
    fun AssetId.bytesValue(): BytesValue =
        bytes.bytesValue()
}
