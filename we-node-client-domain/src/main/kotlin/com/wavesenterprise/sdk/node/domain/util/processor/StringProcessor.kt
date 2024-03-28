package com.wavesenterprise.sdk.node.domain.util.processor

object StringProcessor {
    @Suppress("MagicNumber")
    fun getBytes(value: String): ByteArray {
        val valueBytes = strToBytes(value)
        return concatBytes(numberToBytes(valueBytes.size, 2), valueBytes)
    }
}
