package com.wavesenterprise.sdk.node.domain

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

data class Timestamp(val utcTimestampMillis: Long) {
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
}
