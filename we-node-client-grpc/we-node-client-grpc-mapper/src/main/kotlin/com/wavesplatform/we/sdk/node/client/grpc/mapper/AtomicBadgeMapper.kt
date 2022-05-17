package com.wavesplatform.we.sdk.node.client.grpc.mapper

import com.wavesenterprise.transaction.protobuf.atomicBadge
import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.atomic.AtomicBadge
import com.wavesplatform.we.sdk.node.client.grpc.mapper.AddressMapper.bytesValue
import com.wavesplatform.we.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.transaction.protobuf.AtomicBadge as ProtoAtomicBadge

object AtomicBadgeMapper {
    @JvmStatic
    fun AtomicBadge.dto(): ProtoAtomicBadge =
        dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(atomicBadge: AtomicBadge): ProtoAtomicBadge =
        atomicBadge {
            atomicBadge.trustedSender?.let { domainTrustedSender: Address ->
                trustedSender = domainTrustedSender.bytesValue()
            }
        }

    @JvmStatic
    fun ProtoAtomicBadge.domain(): AtomicBadge =
        domainInternal(this)

    @JvmStatic
    internal fun domainInternal(atomicBadge: ProtoAtomicBadge): AtomicBadge =
        AtomicBadge(
            trustedSender = Address(atomicBadge.trustedSender.byteArray())
        )
}
