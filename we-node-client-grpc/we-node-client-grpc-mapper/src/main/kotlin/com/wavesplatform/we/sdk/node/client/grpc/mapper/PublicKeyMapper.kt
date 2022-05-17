package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesplatform.we.sdk.node.client.PublicKey
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString

object PublicKeyMapper {
    @JvmStatic
    fun PublicKey.byteString(): ByteString =
        bytes.byteString()
}
