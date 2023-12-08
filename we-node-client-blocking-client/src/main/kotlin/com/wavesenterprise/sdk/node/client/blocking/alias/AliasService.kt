package com.wavesenterprise.sdk.node.client.blocking.alias

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Alias
import java.util.Optional

interface AliasService {
    fun getAliasesByAddress(address: Address): List<Alias>
    fun getAddressByAlias(alias: Alias): Optional<Address>
}
