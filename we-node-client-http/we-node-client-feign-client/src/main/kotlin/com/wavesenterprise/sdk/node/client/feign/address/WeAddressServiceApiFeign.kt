package com.wavesenterprise.sdk.node.client.feign.address

import com.wavesenterprise.sdk.node.client.http.DataEntryDto
import com.wavesenterprise.sdk.node.client.http.address.AddressDto
import com.wavesenterprise.sdk.node.client.http.address.SignMessageRequestDto
import com.wavesenterprise.sdk.node.client.http.address.SignMessageResponseDto
import com.wavesenterprise.sdk.node.client.http.address.VerifyMessageSignatureRequestDto
import com.wavesenterprise.sdk.node.client.http.address.VerifyMessageSignatureResponseDto
import feign.Headers
import feign.Param
import feign.RequestLine
import java.util.Optional

interface WeAddressServiceApiFeign {

    @RequestLine("GET /addresses")
    fun getAddresses(): List<String>

    @RequestLine("GET /addresses/publicKey/{publicKey}")
    fun getAddressByPublicKey(@Param("publicKey") publicKey: String): AddressDto

    @RequestLine("GET /addresses/data/{address}/{key}")
    fun getAddressValue(
        @Param("address") address: String,
        @Param("key") key: String
    ): Optional<DataEntryDto>

    @RequestLine("GET /addresses/data/{address}")
    fun getAddressValues(@Param("address") address: String): List<DataEntryDto>

    @Headers("Content-Type: application/json")
    @RequestLine("POST /addresses/sign/{address}")
    fun signMessage(
        @Param("address") address: String,
        request: SignMessageRequestDto,
    ): SignMessageResponseDto

    @Headers("Content-Type: application/json")
    @RequestLine("POST /addresses/verify/{address}")
    fun verifyMessageSignature(
        @Param("address") address: String,
        request: VerifyMessageSignatureRequestDto,
    ): VerifyMessageSignatureResponseDto
}
