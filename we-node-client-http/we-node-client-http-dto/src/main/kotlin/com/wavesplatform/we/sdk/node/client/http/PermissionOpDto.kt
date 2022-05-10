package com.wavesplatform.we.sdk.node.client.http

import com.wavesplatform.we.sdk.node.client.PermissionOp
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.http.OpTypeConstants.fromOpTypeDtoToDomain
import com.wavesplatform.we.sdk.node.client.http.OpTypeConstants.toDto
import com.wavesplatform.we.sdk.node.client.http.RoleConstants.fromRoleDtoToDomain
import com.wavesplatform.we.sdk.node.client.http.RoleConstants.toDto

data class PermissionOpDto(
    val opType: String,
    val role: String,
    val timestamp: Long,
    val dueTimestamp: Long,
) {
    companion object {
        @JvmStatic
        fun PermissionOp.toDto(): PermissionOpDto =
            PermissionOpDto(
                opType = opType.toDto(),
                role = role.toDto(),
                timestamp = timestamp.utcTimestampMillis,
                dueTimestamp = dueTimestamp.utcTimestampMillis,
            )

        @JvmStatic
        fun PermissionOpDto.toDomain(): PermissionOp =
            PermissionOp(
                opType = opType.fromOpTypeDtoToDomain(),
                role = role.fromRoleDtoToDomain(),
                timestamp = Timestamp.fromUtcTimestamp(timestamp),
                dueTimestamp = Timestamp.fromUtcTimestamp(dueTimestamp),
            )
    }
}
