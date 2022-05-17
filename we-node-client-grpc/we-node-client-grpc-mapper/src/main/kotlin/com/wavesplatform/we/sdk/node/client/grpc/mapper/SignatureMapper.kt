package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString

object SignatureMapper {
    @JvmStatic
    fun Signature.byteString(): ByteString =
        bytes.byteString()
}
