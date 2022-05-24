package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.sdk.node.domain.grpc.mapper.GrpcTypesMapper.bytesValue

object AddressMapper {
    @JvmStatic
    fun Address.bytesValue(): BytesValue {
        return bytes.bytesValue()
    }

    @JvmStatic
    fun Address.byteString(): ByteString {
        return bytes.byteString()
    }
}
