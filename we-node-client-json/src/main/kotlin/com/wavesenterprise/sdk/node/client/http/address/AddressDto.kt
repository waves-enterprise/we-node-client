package com.wavesenterprise.sdk.node.client.http.address

import com.wavesenterprise.sdk.node.domain.Address

data class AddressDto(
    val address: String,
) {
    companion object {
        @JvmStatic
        fun AddressDto.toDomain(): Address = Address.fromBase58(address)

        @JvmStatic
        fun Address.toDto(): AddressDto = AddressDto(address = asBase58String())

        @JvmStatic
        fun String.toDomain(): Address = Address.fromBase58(this)
    }
}
