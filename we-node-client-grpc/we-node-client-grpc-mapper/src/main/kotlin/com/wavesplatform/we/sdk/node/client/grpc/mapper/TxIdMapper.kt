package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesplatform.we.sdk.node.client.TxId
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.bytesValue

object TxIdMapper {
    @JvmStatic
    fun TxId.byteString(): ByteString =
        bytes.byteString()

    @JvmStatic
    fun TxId.bytesValue(): BytesValue =
        bytes.bytesValue()
}
