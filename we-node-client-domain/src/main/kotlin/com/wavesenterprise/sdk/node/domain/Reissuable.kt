package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.BoolProcessor

data class Reissuable(val value: Boolean) : SerializableToBytes {
    override fun getSignatureBytes(networkByte: Byte?): ByteArray = BoolProcessor.getBytes(value)
}
