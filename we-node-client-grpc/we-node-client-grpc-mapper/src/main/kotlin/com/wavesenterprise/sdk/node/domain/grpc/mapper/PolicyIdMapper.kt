package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.domain.grpc.mapper.TxIdMapper.bytesValue

object PolicyIdMapper {
    @JvmStatic
    fun PolicyId.byteString(): ByteString =
        txId.byteString()

    @JvmStatic
    fun PolicyId.bytesValue(): BytesValue =
        txId.bytesValue()
}
