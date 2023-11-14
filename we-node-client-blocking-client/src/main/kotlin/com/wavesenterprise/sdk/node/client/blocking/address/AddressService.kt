package com.wavesenterprise.sdk.node.client.blocking.address

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.address.SignMessageRequest
import com.wavesenterprise.sdk.node.domain.address.SignMessageResponse
import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureRequest
import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureResponse
import java.util.Optional

interface AddressService {
    fun getAddresses(): List<Address>
    fun getAddressByPublicKey(publicKey: PublicKey): Address
    fun getAddressValue(address: Address, key: DataKey): Optional<DataEntry>
    fun getAddressValues(address: Address): List<DataEntry>
    fun signMessage(address: Address, request: SignMessageRequest): SignMessageResponse
    fun verifyMessageSignature(address: Address, request: VerifyMessageSignatureRequest): VerifyMessageSignatureResponse
}
