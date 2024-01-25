package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.StringProcessor

data class NodeName(val value: String) : SerializableToBytes {
    override fun getSignatureBytes(networkByte: Byte?): ByteArray = StringProcessor.getBytes(value)
}
