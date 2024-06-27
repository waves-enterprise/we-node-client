package com.wavesenterprise.sdk.node.client.grpc.mapper.tx

import com.wavesenterprise.protobuf.service.util.TransactionInfoResponse
import com.wavesenterprise.sdk.node.client.grpc.mapper.tx.TxMapper.domain
import com.wavesenterprise.sdk.node.domain.Height
import com.wavesenterprise.sdk.node.domain.tx.TxInfo

object TxInfoMapper {
    @JvmStatic
    fun TransactionInfoResponse.domain(): TxInfo =
        TxInfo(
            // todo: ask why height is int32 and in BlockAppended or in AppendedBlockHistory height is int64
            height = Height(height.toLong()),
            tx = transaction.domain(),
        )
}
