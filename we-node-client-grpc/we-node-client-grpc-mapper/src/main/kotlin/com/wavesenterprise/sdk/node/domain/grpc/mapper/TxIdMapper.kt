package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.bytesValue

object TxIdMapper {
    @JvmStatic
    fun TxId.byteString(): ByteString =
        bytes.byteString()

    @JvmStatic
    fun TxId.bytesValue(): BytesValue =
        bytes.bytesValue()
}
