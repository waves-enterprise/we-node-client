package com.wavesplatform.we.sdk.node.client

@JvmInline
value class BlockReference(val bytes: ByteArray) {
    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): BlockReference =
            BlockReference(bytes)

        inline val ByteArray.blockReference: BlockReference get() = BlockReference(this)
    }
}
