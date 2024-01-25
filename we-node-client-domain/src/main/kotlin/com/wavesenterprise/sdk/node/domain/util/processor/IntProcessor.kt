package com.wavesenterprise.sdk.node.domain.util.processor

object IntProcessor {
    @Suppress("MagicNumber")
    fun getBytes(value: Int): ByteArray = numberToBytes(value, 4)
}
