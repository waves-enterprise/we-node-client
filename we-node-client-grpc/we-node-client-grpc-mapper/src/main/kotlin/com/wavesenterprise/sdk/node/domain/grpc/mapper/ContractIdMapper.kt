package com.wavesenterprise.sdk.node.domain.grpc.mapper

import com.google.protobuf.ByteString
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.grpc.mapper.TxIdMapper.byteString

object ContractIdMapper {
    @JvmStatic
    fun ContractId.byteString(): ByteString =
        txId.byteString()
}
