package com.wavesenterprise.sdk.node.domain

data class TxCount(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): TxCount =
            TxCount(value)

        inline val Int.txCount: TxCount get() = TxCount(this)
    }
}
