package com.wavesenterprise.sdk.node.client.feign.address

import com.wavesenterprise.sdk.node.client.blocking.address.AddressService
import com.wavesenterprise.sdk.node.client.http.AddressDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.DataEntryDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.address.SignMessageRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.address.SignMessageResponseDto.Companion.toDomain
import com.wavesenterprise.sdk.node.client.http.address.VerifyMessageSignatureRequestDto.Companion.toDto
import com.wavesenterprise.sdk.node.client.http.address.VerifyMessageSignatureResponseDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Address.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.DataKey
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.address.SignMessageRequest
import com.wavesenterprise.sdk.node.domain.address.SignMessageResponse
import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureRequest
import com.wavesenterprise.sdk.node.domain.address.VerifyMessageSignatureResponse
import com.wavesenterprise.sdk.node.exception.specific.DataKeyNotExistException
import java.util.Optional

class FeignAddressService(
    private val weAddressServiceApiFeign: WeAddressServiceApiFeign,
) : AddressService {
    override fun getAddresses(): List<Address> =
        weAddressServiceApiFeign.getAddresses().map { it.toDomain() }

    override fun getAddressByPublicKey(publicKey: PublicKey): Address =
        weAddressServiceApiFeign.getAddressByPublicKey(publicKey.asBase58String()).toDomain()

    override fun getAddressValue(address: Address, key: DataKey): Optional<DataEntry> =
        try {
            weAddressServiceApiFeign.getAddressValue(
                address = address.asBase58String(),
                key = key.value,
            ).map { it.toDomain() }
        } catch (ex: DataKeyNotExistException) {
            Optional.empty()
        }

    override fun getAddressValues(address: Address): List<DataEntry> =
        weAddressServiceApiFeign.getAddressValues(address.asBase58String()).map { it.toDomain() }

    override fun signMessage(
        address: Address,
        request: SignMessageRequest,
    ): SignMessageResponse =
        weAddressServiceApiFeign.signMessage(
            address = address.asBase58String(),
            request = request.toDto(),
        ).toDomain()

    override fun verifyMessageSignature(
        address: Address,
        request: VerifyMessageSignatureRequest,
    ): VerifyMessageSignatureResponse =
        weAddressServiceApiFeign.verifyMessageSignature(
            address = address.asBase58String(),
            request = request.toDto(),
        ).toDomain()
}
