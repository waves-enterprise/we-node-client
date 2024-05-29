package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.ByteString

internal fun byteString(value: String): ByteString =
    ByteString.copyFromUtf8(value)
