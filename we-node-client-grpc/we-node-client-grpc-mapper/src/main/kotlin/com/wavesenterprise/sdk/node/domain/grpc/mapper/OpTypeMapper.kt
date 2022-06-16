package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.transaction.protobuf.OpType as ProtoOpType

object OpTypeMapper {
    @JvmStatic
    fun OpType.dto(): ProtoOpType =
        when (this) {
            OpType.UNKNOWN -> ProtoOpType.UNKNOWN_OP_TYPE
            OpType.ADD -> ProtoOpType.ADD
            OpType.REMOVE -> ProtoOpType.REMOVE
        }

    @JvmStatic
    fun ProtoOpType.domain(): OpType =
        when (this) {
            ProtoOpType.UNKNOWN_OP_TYPE -> OpType.UNKNOWN
            ProtoOpType.ADD -> OpType.ADD
            ProtoOpType.REMOVE -> OpType.REMOVE
            ProtoOpType.UNRECOGNIZED -> error("OpType unrecognized")
        }
}
