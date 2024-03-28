package com.wavesenterprise.sdk.node.domain.util.processor

object Base64Processor {
    @Suppress("MagicNumber")
    fun getBytes(value: ByteArray) =
        concatBytes(numberToBytes(value.size, 2), value)
}
