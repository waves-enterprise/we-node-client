package com.wavesenterprise.sdk.node.domain

data class Quantity(val value: Long) {
    companion object {
        @JvmStatic
        fun fromLong(value: Long): Quantity =
            Quantity(value)

        inline val Long.quantity: Quantity get() = Quantity(this)
    }
}
