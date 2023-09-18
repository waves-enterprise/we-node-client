package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.Int64Value
import com.wavesenterprise.sdk.node.client.grpc.mapper.OpTypeMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.OpTypeMapper.dto
import com.wavesenterprise.sdk.node.client.grpc.mapper.RoleMapper.domain
import com.wavesenterprise.sdk.node.client.grpc.mapper.RoleMapper.dto
import com.wavesenterprise.sdk.node.domain.PermissionOp
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.transaction.protobuf.permissionOp
import com.wavesenterprise.transaction.protobuf.PermissionOp as ProtoPermissionOp

object PermissionOpMapper {
    @JvmStatic
    fun PermissionOp.dto(): ProtoPermissionOp =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(permissionOp: PermissionOp): ProtoPermissionOp =
        permissionOp {
            opType = permissionOp.opType.dto()
            role = permissionOp.role.dto()
            timestamp = permissionOp.timestamp.utcTimestampMillis
            dueTimestamp = Int64Value.of(permissionOp.dueTimestamp.utcTimestampMillis)
        }

    @JvmStatic
    fun ProtoPermissionOp.domain(): PermissionOp =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(permissionOp: ProtoPermissionOp): PermissionOp =
        PermissionOp(
            opType = permissionOp.opType.domain(),
            role = permissionOp.role.domain(),
            timestamp = Timestamp.fromUtcTimestamp(permissionOp.timestamp),
            dueTimestamp = Timestamp.fromUtcTimestamp(permissionOp.dueTimestamp.value),
        )
}
