package com.wavesplatform.we.sdk.node.client

data class DataKey(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): DataKey =
            DataKey(value)

        inline val String.dataKey: DataKey get() = DataKey(this)
    }
}
