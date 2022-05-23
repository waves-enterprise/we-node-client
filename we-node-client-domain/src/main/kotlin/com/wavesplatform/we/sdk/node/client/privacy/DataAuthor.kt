package com.wavesplatform.we.sdk.node.client.privacy

data class DataAuthor(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): DataAuthor =
            DataAuthor(value)

        inline val String.dataAuthor: DataAuthor get() = DataAuthor(this)
    }
}
