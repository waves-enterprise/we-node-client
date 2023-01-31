package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.bytesValue
import com.wavesenterprise.sdk.node.domain.TxId

object TxIdMapper {
    @JvmStatic
    fun TxId.byteString(): ByteString =
        bytes.byteString()

    @JvmStatic
    fun TxId.bytesValue(): BytesValue =
        bytes.bytesValue()
}
