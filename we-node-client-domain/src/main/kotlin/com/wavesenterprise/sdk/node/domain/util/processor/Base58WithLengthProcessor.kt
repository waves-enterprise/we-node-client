package com.wavesenterprise.sdk.node.domain.util.processor

object Base58WithLengthProcessor {
    fun getBytes(value: ByteArray, length: Long = 2): ByteArray {
        val lengthBytes = numberToBytes(value.size, length)
        return concatBytes(lengthBytes, value)
    }
}
