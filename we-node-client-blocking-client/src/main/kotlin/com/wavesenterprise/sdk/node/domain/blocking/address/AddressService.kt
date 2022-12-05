package com.wavesenterprise.sdk.node.domain.blocking.address

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry

interface AddressService {
    fun getAddresses(): List<Address>
    fun getAddressByPublicKey(publicKey: String): Address
    fun getAddressValue(address: String, key: String): DataEntry?
    fun getAddressValues(address: String): List<DataEntry>
}
