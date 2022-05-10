package com.wavesplatform.we.sdk.node.client.http

import com.wavesplatform.we.sdk.node.client.OpType

object OpTypeConstants {
    const val UNKNOWN = "unknown"
    const val ADD = "add"
    const val REMOVE = "remove"

    @JvmStatic
    fun OpType.toDto(): String =
        when (this) {
            OpType.UNKNOWN -> UNKNOWN
            OpType.ADD -> ADD
            OpType.REMOVE -> REMOVE
        }

    @JvmStatic
    fun String.fromOpTypeDtoToDomain(): OpType =
        when (this) {
            UNKNOWN -> OpType.UNKNOWN
            ADD -> OpType.ADD
            REMOVE -> OpType.REMOVE
            else -> error("Unknown op type $this")
        }
}
