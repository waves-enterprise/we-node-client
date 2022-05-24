package com.wavesenterprise.sdk.node.domain

data class PermissionOp(
    val opType: OpType,
    val role: Role,
    val timestamp: Timestamp,
    val dueTimestamp: Timestamp,
)
