package com.wavesplatform.we.sdk.node.client

@JvmInline
value class MajorVersion(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): MajorVersion =
            MajorVersion(value)

        inline val Int.majorVersion: MajorVersion get() = MajorVersion(this)
    }
}
