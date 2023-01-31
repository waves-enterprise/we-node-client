package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.bytesValue
import com.wavesenterprise.sdk.node.domain.Address

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
