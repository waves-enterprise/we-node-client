package com.wavesplatform.we.sdk.node.client.event

import com.wavesplatform.we.sdk.node.client.ContractId
import com.wavesplatform.we.sdk.node.client.TxType

@BlockchainEventsDsl
interface EventsFilterContext {
    fun includeTypes(types: List<TxType>)
    fun includeTypes(vararg types: TxType)
    fun excludeTypes(types: List<TxType>)
    fun excludeTypes(vararg types: TxType)
    fun includeContracts(ids: List<ContractId>)
    fun excludeContracts(ids: List<ContractId>)
}
