package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.concatBytes
import com.wavesenterprise.sdk.node.domain.util.processor.numberToBytes

data class ContractApiVersion(
    val major: MajorVersion,
    val minor: MinorVersion,
) : SerializableToBytes {
    override fun getSignatureBytes(networkByte: Byte?): ByteArray {
        val majorBytes = numberToBytes(major.value, 2)
        val minorBytes = numberToBytes(minor.value, 2)
        return concatBytes(majorBytes, minorBytes)
    }
}
