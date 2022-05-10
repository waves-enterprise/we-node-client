package com.wavesplatform.we.sdk.node.client

data class PermissionOp(
    val opType: OpType,
    val role: Role,
    val timestamp: Timestamp,
    val dueTimestamp: Timestamp,
)
