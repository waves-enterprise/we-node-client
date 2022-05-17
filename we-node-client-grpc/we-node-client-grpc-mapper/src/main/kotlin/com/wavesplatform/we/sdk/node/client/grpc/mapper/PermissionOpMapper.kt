package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.wavesenterprise.transaction.protobuf.PermissionOp as ProtoPermissionOp
import com.google.protobuf.Int64Value
import com.wavesenterprise.transaction.protobuf.permissionOp
import com.wavesplatform.we.sdk.node.client.PermissionOp
import com.wavesplatform.we.sdk.node.client.Timestamp
import com.wavesplatform.we.sdk.node.client.grpc.mapper.OpTypeMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.OpTypeMapper.dto
import com.wavesplatform.we.sdk.node.client.grpc.mapper.RoleMapper.domain
import com.wavesplatform.we.sdk.node.client.grpc.mapper.RoleMapper.dto

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
