package com.wavesplatform.we.sdk.node.client

@JvmInline
value class Alias(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(str: String): Alias =
            Alias(str)

        inline val String.alias: Alias get() = Alias(this)
    }
}
