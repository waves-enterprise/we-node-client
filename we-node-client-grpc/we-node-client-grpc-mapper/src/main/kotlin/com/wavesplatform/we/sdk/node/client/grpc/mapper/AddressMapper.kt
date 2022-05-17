package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.bytesValue

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
