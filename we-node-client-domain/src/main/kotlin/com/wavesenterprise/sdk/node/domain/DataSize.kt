package com.wavesenterprise.sdk.node.domain

data class DataSize(val bytesCount: Long) {
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

        const val DATA_SIZE_MULTIPLIER = 1_024L

        // todo division instead of substraction?
        inline val Long.kilobytes: DataSize get() = DataSize(this * DATA_SIZE_MULTIPLIER)

        inline val Long.megabytes: DataSize get() = DataSize(this.kilobytes.bytesCount * DATA_SIZE_MULTIPLIER)

        inline val Long.gigabytes: DataSize get() = DataSize(this.megabytes.bytesCount * DATA_SIZE_MULTIPLIER)
    }
}
