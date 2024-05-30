package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Amount
import com.wavesenterprise.sdk.node.domain.Transfer
import com.wavesenterprise.transaction.protobuf.Transfer as ProtoTransfer

object TransferMapper {

    @JvmStatic
    fun ProtoTransfer.domain(): Transfer =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(protoTransfer: ProtoTransfer): Transfer =
        Transfer(
            recipient = Address(protoTransfer.recipient.byteArray()),
            amount = Amount(protoTransfer.amount),
        )
}
