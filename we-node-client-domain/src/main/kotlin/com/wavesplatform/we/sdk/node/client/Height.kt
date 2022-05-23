package com.wavesplatform.we.sdk.node.client

data class Height(val value: Long) {
    companion object {
        @JvmStatic
        fun fromLong(value: Long): Height =
            Height(value)

        inline val Long.height: Height get() = Height(this)
    }
}
