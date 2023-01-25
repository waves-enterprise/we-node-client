package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.BytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.bytesValue
import com.wavesenterprise.sdk.node.domain.FeeAssetId

object FeeAssetIdMapper {
    @JvmStatic
    fun FeeAssetId.bytesValue(): BytesValue =
        txId.bytesValue()
}
