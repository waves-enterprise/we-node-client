package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.bytesValue

object PolicyIdMapper {
    @JvmStatic
    fun PolicyId.byteString(): ByteString =
        txId.byteString()

    @JvmStatic
    fun PolicyId.bytesValue(): BytesValue =
        txId.bytesValue()
}
