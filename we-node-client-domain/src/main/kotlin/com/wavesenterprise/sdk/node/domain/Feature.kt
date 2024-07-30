package com.wavesenterprise.sdk.node.domain

data class Feature(val code: Int) {
    companion object {
        @JvmStatic
        fun fromInt(code: Int): Feature =
            Feature(code)

        @Suppress("MemberNameEqualsClassName")
        inline val Int.feature: Feature get() = Feature(this)
    }
}
