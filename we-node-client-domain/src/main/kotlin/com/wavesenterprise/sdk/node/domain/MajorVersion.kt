package com.wavesenterprise.sdk.node.domain

data class MajorVersion(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): MajorVersion =
            MajorVersion(value)

        @Suppress("MemberNameEqualsClassName")
        inline val Int.majorVersion: MajorVersion get() = MajorVersion(this)
    }
}
