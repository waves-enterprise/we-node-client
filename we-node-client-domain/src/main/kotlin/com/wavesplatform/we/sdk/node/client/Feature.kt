package com.wavesplatform.we.sdk.node.client

@JvmInline
value class Feature(val code: Int) {
    companion object {
        @JvmStatic
        fun fromInt(code: Int): Feature =
            Feature(code)

        inline val Int.feature: Feature get() = Feature(this)
    }
}
