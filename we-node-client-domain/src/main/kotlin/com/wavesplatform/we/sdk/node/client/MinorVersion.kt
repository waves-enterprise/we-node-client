package com.wavesplatform.we.sdk.node.client

@JvmInline
value class MinorVersion(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): MinorVersion =
            MinorVersion(value)

        inline val Int.minorVersion: MinorVersion get() = MinorVersion(this)
    }
}
