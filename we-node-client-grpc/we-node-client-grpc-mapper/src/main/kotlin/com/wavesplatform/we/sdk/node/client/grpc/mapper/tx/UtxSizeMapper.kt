package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesplatform.we.sdk.node.client.DataSize
import com.wavesplatform.we.sdk.node.client.TxCount
import com.wavesplatform.we.sdk.node.client.tx.UtxSize
import com.wavesenterprise.protobuf.service.transaction.TransactionPublicServiceOuterClass.UtxSize as ProtoUtxSize

object UtxSizeMapper {
    @JvmStatic
    fun ProtoUtxSize.domain(): UtxSize =
        UtxSize(
            txCount = TxCount(size),
            size = DataSize(sizeInBytes),
        )
}
