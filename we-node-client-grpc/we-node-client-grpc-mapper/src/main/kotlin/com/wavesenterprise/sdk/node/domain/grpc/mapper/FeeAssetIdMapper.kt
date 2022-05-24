package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.grpc.mapper.TxIdMapper.bytesValue

object FeeAssetIdMapper {
    @JvmStatic
    fun FeeAssetId.bytesValue(): BytesValue =
        txId.bytesValue()
}
