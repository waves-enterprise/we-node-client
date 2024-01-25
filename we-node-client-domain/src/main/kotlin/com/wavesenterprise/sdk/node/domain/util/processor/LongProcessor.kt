package com.wavesenterprise.sdk.node.domain.util.processor

object LongProcessor {
    @Suppress("MagicNumber")
    fun getBytes(value: Long) = numberToBytes(value, 8)
}
