package com.wavesplatform.we.sdk.node.client

@JvmInline
value class ChainId(val value: Byte) {
    companion object {
        @JvmStatic
        fun fromByte(value: Byte): ChainId =
            ChainId(value)

        inline val Byte.chainId: ChainId get() = ChainId(this)
    }
}
