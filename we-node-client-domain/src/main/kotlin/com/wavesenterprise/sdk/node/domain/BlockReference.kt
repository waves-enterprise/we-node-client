package com.wavesenterprise.sdk.node.domain

data class BlockReference(val bytes: ByteArray) {
    companion object {
        @JvmStatic
        fun fromByteArray(bytes: ByteArray): BlockReference =
            BlockReference(bytes)

        inline val ByteArray.blockReference: BlockReference get() = BlockReference(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BlockReference

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
