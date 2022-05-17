package com.wavesplatform.we.sdk.node.client

@JvmInline
value class DataSize(val bytesCount: Long) {
    init {
        check(bytesCount >= 0) {
            "Data size should be a non-negative number"
        }
    }

    companion object {
        @JvmStatic
        fun ofBytes(bytesCount: Long): DataSize =
            DataSize(bytesCount)

        inline val Long.bytes: DataSize get() = DataSize(this)

        inline val Long.kilobytes: DataSize get() = DataSize(this * 1_000L)

        inline val Long.megabytes: DataSize get() = DataSize(this * 1_000_000L)

        inline val Long.gigabytes: DataSize get() = DataSize(this * 1_000_000_000L)
    }
}
