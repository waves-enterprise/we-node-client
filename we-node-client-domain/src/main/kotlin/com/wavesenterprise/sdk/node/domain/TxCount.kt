package com.wavesenterprise.sdk.node.domain

data class TxCount(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): TxCount =
            TxCount(value)

        @Suppress("MemberNameEqualsClassName")
        inline val Int.txCount: TxCount get() = TxCount(this)
    }
}
