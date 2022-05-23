package com.wavesplatform.we.sdk.node.client.privacy

data class DataComment(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): DataComment =
            DataComment(value)

        inline val String.dataComment: DataComment get() = DataComment(this)
    }
}
