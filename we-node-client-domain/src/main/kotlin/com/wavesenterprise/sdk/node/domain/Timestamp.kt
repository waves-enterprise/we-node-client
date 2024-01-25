package com.wavesenterprise.sdk.node.domain

import com.wavesenterprise.sdk.node.domain.sign.SerializableToBytes
import com.wavesenterprise.sdk.node.domain.util.processor.LongProcessor
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

data class Timestamp(val utcTimestampMillis: Long) : SerializableToBytes {
    companion object {
        @JvmStatic
        fun fromUtcTimestamp(utcTimestampMillis: Long): Timestamp =
            Timestamp(utcTimestampMillis)

        @JvmStatic
        fun Timestamp.toDateTimeFromUTCBlockChain(): OffsetDateTime =
            OffsetDateTime.ofInstant(Instant.ofEpochMilli(this.utcTimestampMillis), ZoneId.of("UTC"))

        @JvmStatic
        fun Long.toDateTimeFromUTCBlockChain(): OffsetDateTime =
            OffsetDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.of("UTC"))

        inline val Long.utcTimestampMillis: Timestamp get() = Timestamp(this)
    }

    override fun getSignatureBytes(networkByte: Byte?): ByteArray = LongProcessor.getBytes(utcTimestampMillis)
}
