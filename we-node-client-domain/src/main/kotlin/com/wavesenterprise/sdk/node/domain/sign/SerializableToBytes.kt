package com.wavesenterprise.sdk.node.domain.sign

interface SerializableToBytes {
    fun getSignatureBytes(networkByte: Byte? = null): ByteArray
}
