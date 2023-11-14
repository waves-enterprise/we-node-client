package com.wavesenterprise.sdk.node.client.grpc.blocking.node

import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.address.SignMessageRequest
import com.wavesenterprise.sdk.node.domain.address.SignMessageResponse
import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureRequest
import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureResponse
import java.util.Optional

class AddressGrpcBlockingService : AddressService {
    override fun getAddresses(): List<Address> {
        TODO("Not yet implemented")
    }

    override fun getAddressByPublicKey(publicKey: PublicKey): Address {
        TODO("Not yet implemented")
    }

    override fun getAddressValue(address: Address, key: DataKey): Optional<DataEntry> {
        TODO("Not yet implemented")
    }

    override fun getAddressValues(address: Address): List<DataEntry> {
        TODO("Not yet implemented")
    }

    override fun signMessage(
        address: Address,
        request: SignMessageRequest
    ): SignMessageResponse {
        TODO("Not yet implemented")
    }

    override fun verifyMessageSignature(
        address: Address,
        request: VerifyMessageSignatureRequest
    ): VerifyMessageSignatureResponse {
        TODO("Not yet implemented")
    }
}
