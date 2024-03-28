package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.LongProcessor
import com.wavesenterprise.sdk.node.domain.util.processor.concatBytes

data class PermissionOp(
    val opType: OpType,
    val role: Role,
    val timestamp: Timestamp,
    val dueTimestamp: Timestamp,
) : SerializableToBytes {
    override fun getSignatureBytes(networkByte: Byte?): ByteArray {
        val opTypeBytes = opType.getSignatureBytes()
        val roleBytes = role.getSignatureBytes()
        val timestampBytes = LongProcessor.getBytes(timestamp.utcTimestampMillis)
        val dueTimestampBytes = LongProcessor.getBytes(dueTimestamp.utcTimestampMillis)
        return concatBytes(opTypeBytes, roleBytes, timestampBytes, dueTimestampBytes)
    }
}
