package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.BytesValue
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.bytesValue

object FeeAssetIdMapper {
    @JvmStatic
    fun FeeAssetId.bytesValue(): BytesValue =
        txId.bytesValue()
}
