package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.wavesenterprise.transaction.protobuf.contractApiVersion
import com.wavesplatform.we.sdk.node.client.ContractApiVersion
import com.wavesplatform.we.sdk.node.client.MajorVersion
import com.wavesplatform.we.sdk.node.client.MinorVersion
import com.wavesenterprise.transaction.protobuf.ContractApiVersion as ProtoContractApiVersion

object ContractApiVersionMapper {
    @JvmStatic
    fun ContractApiVersion.dto(): ProtoContractApiVersion =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(contractApiVersion: ContractApiVersion): ProtoContractApiVersion =
        contractApiVersion {
            majorVersion = contractApiVersion.major.value
            minorVersion = contractApiVersion.minor.value
        }

    @JvmStatic
    fun ProtoContractApiVersion.domain(): ContractApiVersion =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(contractApiVersion: ProtoContractApiVersion): ContractApiVersion =
        ContractApiVersion(
            major = MajorVersion(contractApiVersion.majorVersion),
            minor = MinorVersion(contractApiVersion.minorVersion),
        )
}
