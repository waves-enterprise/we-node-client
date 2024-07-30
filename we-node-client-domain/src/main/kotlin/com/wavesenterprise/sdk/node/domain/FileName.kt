package com.wavesenterprise.sdk.node.domain

data class FileName(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): FileName =
            FileName(value)

        @Suppress("MemberNameEqualsClassName")
        inline val String.fileName: FileName get() = FileName(this)
    }
}
