package com.wavesenterprise.sdk.node.client.http.node

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.NodeOwner
import com.wavesenterprise.sdk.node.domain.PublicKey

data class NodeOwnerDto(
    val address: String,
    val publicKey: String,
) {
    companion object {
        @JvmStatic
        fun NodeOwnerDto.toDomain() =
            NodeOwner(
                address = Address.fromBase58(address),
                publicKey = PublicKey.fromBase58(publicKey),
            )
    }
}
