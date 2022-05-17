package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString

object HashMapper {
    @JvmStatic
    fun Hash.byteString(): ByteString =
        bytes.byteString()
}
