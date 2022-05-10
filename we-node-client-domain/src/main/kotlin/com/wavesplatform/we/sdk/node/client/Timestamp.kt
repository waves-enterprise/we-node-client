package com.wavesplatform.we.sdk.node.client

@JvmInline
value class Timestamp(val utcTimestampMillis: Long) {
    companion object {
        @JvmStatic
        fun fromUtcTimestamp(utcTimestampMillis: Long): Timestamp =
            Timestamp(utcTimestampMillis)

        inline val Long.utcTimestampMillis: Timestamp get() = Timestamp(this)
    }
}
