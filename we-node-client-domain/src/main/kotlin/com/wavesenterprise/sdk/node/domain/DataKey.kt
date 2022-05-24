package com.wavesenterprise.sdk.node.domain

data class DataKey(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): DataKey =
            DataKey(value)

        inline val String.dataKey: DataKey get() = DataKey(this)
    }
}
