package com.wavesplatform.we.sdk.node.client

@JvmInline
value class TxCount(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): TxCount =
            TxCount(value)

        inline val Int.txCount: TxCount get() = TxCount(this)
    }
}
