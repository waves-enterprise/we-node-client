package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.client.grpc.mapper.TxIdMapper.byteString
import com.wavesenterprise.sdk.node.domain.contract.ContractId

object ContractIdMapper {
    @JvmStatic
    fun ContractId.byteString(): ByteString =
        txId.byteString()
}
