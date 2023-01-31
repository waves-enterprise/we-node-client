package com.wavesenterprise.sdk.node.client.grpc.mapper

import com.wavesenterprise.sdk.node.client.grpc.mapper.AddressMapper.bytesValue
import com.wavesenterprise.sdk.node.client.grpc.mapper.GrpcTypesMapper.byteArray
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.transaction.protobuf.atomicBadge
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
