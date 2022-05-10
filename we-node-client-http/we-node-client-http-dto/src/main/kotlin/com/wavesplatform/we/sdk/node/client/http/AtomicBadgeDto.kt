package com.wavesplatform.we.sdk.node.client.http

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AtomicBadge

data class AtomicBadgeDto(val trustedSender: String? = null) {
    companion object {
        @JvmStatic
        fun AtomicBadge.toDto(): AtomicBadgeDto =
            AtomicBadgeDto(
                trustedSender = trustedSender?.asBase58String()
            )

        @JvmStatic
        fun AtomicBadgeDto.toDomain(): AtomicBadge =
            AtomicBadge(
                trustedSender = trustedSender?.let {
                    Address.fromBase58(it)
                }
            )
    }
}
