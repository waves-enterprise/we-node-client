package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.bytesValue
import com.wavesenterprise.sdk.node.domain.Attachment

object AttachmentMapper {
    @JvmStatic
    fun Attachment.byteString(): ByteString =
        bytes.byteString()

    @JvmStatic
    fun Attachment.bytesValue(): BytesValue =
        bytes.bytesValue()
}
