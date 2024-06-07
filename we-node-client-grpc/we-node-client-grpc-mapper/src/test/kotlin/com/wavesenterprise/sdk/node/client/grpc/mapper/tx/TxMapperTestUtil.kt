package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.google.protobuf.ByteString

internal fun byteString(value: String): ByteString =
    ByteString.copyFromUtf8(value)

internal fun <T, U> cartesianProduct(i1: Iterable<T>, i2: Iterable<U>): List<Pair<T, U>> =
    i1.flatMap { first ->
        i2.map { second ->
            first to second
        }
    }
