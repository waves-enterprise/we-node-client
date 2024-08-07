package com.wavesenterprise.sdk.node.domain.node

data class NodeVersion(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): NodeVersion =
            NodeVersion(value)

        @Suppress("MemberNameEqualsClassName")
        inline val String.nodeVersion: NodeVersion get() = NodeVersion(this)
    }
}
