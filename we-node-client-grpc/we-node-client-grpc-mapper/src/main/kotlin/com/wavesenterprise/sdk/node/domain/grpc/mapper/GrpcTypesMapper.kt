package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.google.protobuf.StringValue

object GrpcTypesMapper {
    @JvmStatic
    inline fun ByteArray.byteString(): ByteString =
        ByteString.copyFrom(this)

    @JvmStatic
    inline fun ByteArray.bytesValue(): BytesValue =
        BytesValue.parseFrom(this)

    @JvmStatic
    inline fun String.stringValue(): StringValue =
        StringValue.of(this)

    @JvmStatic
    inline fun ByteString.byteArray(): ByteArray =
        toByteArray()

    @JvmStatic
    inline fun BytesValue.byteArray(): ByteArray =
        toByteArray()
}
