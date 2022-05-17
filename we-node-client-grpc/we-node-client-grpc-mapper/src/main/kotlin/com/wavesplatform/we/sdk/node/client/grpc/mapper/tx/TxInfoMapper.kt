package com.wavesplatform.we.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.protobuf.service.util.TransactionInfoResponse
import com.wavesplatform.we.sdk.node.client.Height
import com.wavesplatform.we.sdk.node.client.grpc.mapper.tx.TxMapper.domain
import com.wavesplatform.we.sdk.node.client.tx.TxInfo

object TxInfoMapper {
    @JvmStatic
    fun TransactionInfoResponse.domain(): TxInfo =
        TxInfo(
            height = Height(height.toLong()), // todo: ask why height is int32 and in BlockAppended or in AppendedBlockHistory height is int64
            tx = transaction.domain(),
        )
}
