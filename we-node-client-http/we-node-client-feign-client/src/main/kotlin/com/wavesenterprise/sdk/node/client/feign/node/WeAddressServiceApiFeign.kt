package com.wavesenterprise.sdk.node.client.feign.node

import com.wavesenterprise.sdk.node.client.http.AddressDto
import feign.Headers
import feign.RequestLine

interface WeAddressServiceApiFeign {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /addresses")
    fun getAddresses(): List<String>

    @Headers("Content-Type: application/json")
    @RequestLine("GET /addresses")
    fun getAddressByPublicKey(publicKey: String): AddressDto
}
