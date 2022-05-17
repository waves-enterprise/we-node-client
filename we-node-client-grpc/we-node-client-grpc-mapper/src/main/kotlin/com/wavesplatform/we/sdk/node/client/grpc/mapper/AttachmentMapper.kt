package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.google.protobuf.BytesValue
import com.wavesplatform.we.sdk.node.client.Attachment
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteString
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.bytesValue

object AttachmentMapper {
    @JvmStatic
    fun Attachment.byteString(): ByteString =
        bytes.byteString()

    @JvmStatic
    fun Attachment.bytesValue(): BytesValue =
        bytes.bytesValue()
}
