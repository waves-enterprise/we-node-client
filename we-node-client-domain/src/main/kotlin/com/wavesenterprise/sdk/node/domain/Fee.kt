package com.wavesenterprise.sdk.node.domain

data class Fee(val value: Long) {
    init {
        check(value >= 0) {
            "Fee value should be a non-negative number"
        }
    }

    companion object {
        @JvmStatic
        fun fromLong(value: Long): Fee =
            Fee(value)

        @JvmStatic
        fun fromInt(value: Int): Fee =
            Fee(value.toLong())

        inline val Long.fee: Fee get() = Fee(this)

        inline val Int.fee: Fee get() = Fee(this.toLong())
    }
}
