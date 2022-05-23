package com.wavesplatform.we.sdk.node.client.node

data class NodeVersion(val value: String) {
    companion object {
        @JvmStatic
        fun fromString(value: String): NodeVersion =
            NodeVersion(value)

        inline val String.nodeVersion: NodeVersion get() = NodeVersion(this)
    }
}
