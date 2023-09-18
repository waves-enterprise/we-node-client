package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.sdk.node.domain.DataSize
import com.wavesenterprise.sdk.node.domain.TxCount
import com.wavesenterprise.sdk.node.domain.tx.UtxSize
import com.wavesenterprise.protobuf.service.transaction.TransactionPublicServiceOuterClass.UtxSize as ProtoUtxSize

object UtxSizeMapper {
    @JvmStatic
    fun ProtoUtxSize.domain(): UtxSize =
        UtxSize(
            txCount = TxCount(size),
            size = DataSize(sizeInBytes),
        )
}
