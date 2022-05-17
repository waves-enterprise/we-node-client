package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesplatform.we.sdk.node.client.AssetId
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.bytesValue

object AssetIdMapper {
    @JvmStatic
    fun AssetId.byteString(): ByteString =
        bytes.byteString()

    @JvmStatic
    fun AssetId.bytesValue(): BytesValue =
        bytes.bytesValue()
}
