package com.wavesplatform.we.sdk.node.client

data class Amount(val value: Long) {
    companion object {
        @JvmStatic
        fun fromLong(value: Long): Amount =
            Amount(value)

        inline val Long.amount: Amount get() = Amount(this)
    }
}
