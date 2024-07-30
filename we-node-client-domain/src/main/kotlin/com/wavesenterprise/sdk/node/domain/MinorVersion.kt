package com.wavesenterprise.sdk.node.domain

data class MinorVersion(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): MinorVersion =
            MinorVersion(value)

        @Suppress("MemberNameEqualsClassName")
        inline val Int.minorVersion: MinorVersion get() = MinorVersion(this)
    }
}
