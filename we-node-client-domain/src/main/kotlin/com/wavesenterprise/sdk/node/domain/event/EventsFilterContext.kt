package com.wavesenterprise.sdk.node.domain.event

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.contract.ContractId

@BlockchainEventsDsl
interface EventsFilterContext {
    fun includeTypes(types: List<TxType>)
    fun includeTypes(vararg types: TxType)
    fun excludeTypes(types: List<TxType>)
    fun excludeTypes(vararg types: TxType)
    fun includeContracts(ids: List<ContractId>)
    fun excludeContracts(ids: List<ContractId>)
}
