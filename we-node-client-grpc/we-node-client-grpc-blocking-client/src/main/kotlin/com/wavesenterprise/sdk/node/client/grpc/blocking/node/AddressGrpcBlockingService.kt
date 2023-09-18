package com.wavesenterprise.sdk.node.client.grpc.blocking.node

import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry

class AddressGrpcBlockingService : AddressService {
    override fun getAddresses(): List<Address> {
        TODO("Not yet implemented")
    }

    override fun getAddressByPublicKey(publicKey: String): Address {
        TODO("Not yet implemented")
    }

    override fun getAddressValue(address: String, key: String): DataEntry? {
        TODO("Not yet implemented")
    }

    override fun getAddressValues(address: String): List<DataEntry> {
        TODO("Not yet implemented")
    }
}
