package com.wavesplatform.we.sdk.node.client.node

@JvmInline
value class NodeVersion(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): NodeVersion =
            NodeVersion(value)

        inline val String.nodeVersion: NodeVersion get() = NodeVersion(this)
    }
}
