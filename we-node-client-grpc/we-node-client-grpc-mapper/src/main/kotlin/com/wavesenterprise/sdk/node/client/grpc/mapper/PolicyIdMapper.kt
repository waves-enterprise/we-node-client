package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.bytesValue
import com.wavesenterprise.sdk.node.domain.PolicyId

object PolicyIdMapper {
    @JvmStatic
    fun PolicyId.byteString(): ByteString =
        txId.byteString()

    @JvmStatic
    fun PolicyId.bytesValue(): BytesValue =
        txId.bytesValue()
}
