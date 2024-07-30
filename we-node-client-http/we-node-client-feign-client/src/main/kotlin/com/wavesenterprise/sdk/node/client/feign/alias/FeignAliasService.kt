package com.wavesenterprise.sdk.node.client.feign.alias

import com.wavesenterprise.sdk.node.client.blocking.alias.AliasService
import com.wavesenterprise.sdk.node.client.http.AddressDto.Companion.toDomain
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Alias
import com.wavesenterprise.sdk.node.domain.Alias.Companion.toDomain
import com.wavesenterprise.sdk.node.exception.specific.AliasNotExistException
import java.util.Optional

class FeignAliasService(
    private val weAliasServiceApiFeign: WeAliasServiceApiFeign,
) : AliasService {
    override fun getAliasesByAddress(address: Address): List<Alias> =
        weAliasServiceApiFeign.getAliasesByAddress(address.asBase58String()).map { it.toDomain() }

    @Suppress("SwallowedException")
    override fun getAddressByAlias(alias: Alias): Optional<Address> =
        try {
            weAliasServiceApiFeign.getAddressByAlias(alias.value).map { it.toDomain() }
        } catch (ex: AliasNotExistException) {
            Optional.empty()
        }
}
