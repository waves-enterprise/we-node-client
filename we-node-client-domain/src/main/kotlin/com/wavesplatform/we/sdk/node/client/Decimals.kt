package com.wavesplatform.we.sdk.node.client

data class Decimals(val value: Byte) {
    companion object {
        @JvmStatic
        fun fromByte(value: Byte): Decimals =
            Decimals(value)

        inline val Byte.decimals: Decimals get() = Decimals(this)
    }
}
