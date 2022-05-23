package com.wavesplatform.we.sdk.node.client

data class FileName(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): FileName =
            FileName(value)

        inline val String.fileName: FileName get() = FileName(this)
    }
}
