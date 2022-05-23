package com.wavesplatform.we.sdk.node.client

data class BlockVersion(val value: Int) {
    companion object {
        @JvmStatic
        fun fromInt(value: Int): BlockVersion =
            BlockVersion(value)

        inline val Int.blockVersion: BlockVersion get() = BlockVersion(this)
    }
}
