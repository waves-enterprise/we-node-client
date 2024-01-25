package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes

enum class OpType : SerializableToBytes {
    UNKNOWN,
    ADD,
    REMOVE,
    ;

    override fun getSignatureBytes(networkByte: Byte?): ByteArray =
        byteArrayOf(
            if (this == ADD) {
                'a'.code.toByte()
            } else {
                'r'.code.toByte()
            }
        )
}
