package com.wavesenterprise.sdk.node.domain.http

import com.wavesenterprise.sdk.node.domain.PermissionOp
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.http.OpTypeConstants.fromOpTypeDtoToDomain
import com.wavesenterprise.sdk.node.domain.http.OpTypeConstants.toDto
import com.wavesenterprise.sdk.node.domain.http.RoleConstants.fromRoleDtoToDomain
import com.wavesenterprise.sdk.node.domain.http.RoleConstants.toDto

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
