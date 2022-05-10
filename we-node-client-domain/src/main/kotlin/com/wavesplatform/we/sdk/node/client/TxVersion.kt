package com.wavesplatform.we.sdk.node.client

@JvmInline
value class TxVersion(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): TxVersion =
            TxVersion(value)

        inline val Int.txVersion: TxVersion get() = TxVersion(this)
    }
}
