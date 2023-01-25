package com.wavesenterprise.sdk.node.client.feign.node

import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry

class FeignAddressService(
    private val weAddressServiceApiFeign: WeAddressServiceApiFeign,
) : AddressService {
    override fun getAddresses(): List<Address> =
        weAddressServiceApiFeign.getAddresses().map { Address.fromBase58(it) }

    override fun getAddressByPublicKey(publicKey: String): Address =
        Address.fromBase58(weAddressServiceApiFeign.getAddressByPublicKey(publicKey).address)

    override fun getAddressValue(address: String, key: String): DataEntry? {
        TODO("Not yet implemented")
    }

    override fun getAddressValues(address: String): List<DataEntry> {
        TODO("Not yet implemented")
    }
}
