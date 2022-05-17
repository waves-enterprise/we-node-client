package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesplatform.we.sdk.node.client.ContractId
import com.wavesplatform.we.sdk.node.client.grpc.mapper.TxIdMapper.byteString

object ContractIdMapper {
    @JvmStatic
    fun ContractId.byteString(): ByteString =
        txId.byteString()
}
