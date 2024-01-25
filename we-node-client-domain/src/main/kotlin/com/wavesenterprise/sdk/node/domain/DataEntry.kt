package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.ParamsProcessor

data class DataEntry(
    val key: DataKey,
    val value: DataValue,
) : SerializableToBytes {
    override fun getSignatureBytes(networkByte: Byte?): ByteArray = ParamsProcessor.getBytes(this)
}
