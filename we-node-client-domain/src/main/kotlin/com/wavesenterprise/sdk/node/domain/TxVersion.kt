package com.wavesenterprise.sdk.node.domain

data class TxVersion(val value: Int) {
    companion object {
        @JvmStatic
        @JvmName("fromInt")
        fun fromInt(value: Int): TxVersion =
            TxVersion(value)

        inline val Int.txVersion: TxVersion get() = TxVersion(this)
    }
}
