package com.wavesenterprise.sdk.node.client.grpc.blocking.alias

import com.wavesenterprise.sdk.node.client.blocking.alias.AliasService
import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Alias
import java.util.Optional

class AliasGrpcBlockingService : AliasService {
    override fun getAliasesByAddress(address: Address): List<Alias> {
        TODO("Not yet implemented")
    }

    override fun getAddressByAlias(alias: Alias): Optional<Address> {
        TODO("Not yet implemented")
    }
}
