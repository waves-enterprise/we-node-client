package com.wavesenterprise.sdk.node.domain.event

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.contract.ContractId

class EventsFilterContextImpl : EventsFilterContext {
    private val filters: MutableList<EventsFilter> = mutableListOf()

    override fun includeTypes(types: List<TxType>) {
        filters.add(
            EventsFilter(
                type = FilterType.IN,
                filter = Filter.TxTypeFilter(types),
            ),
        )
    }

    override fun includeTypes(vararg types: TxType) =
        includeTypes(types.toList())

    override fun excludeTypes(types: List<TxType>) {
        filters.add(
            EventsFilter(
                type = FilterType.OUT,
                filter = Filter.TxTypeFilter(types),
            ),
        )
    }

    override fun excludeTypes(vararg types: TxType) =
        excludeTypes(types.toList())

    override fun includeContracts(ids: List<ContractId>) {
        filters.add(
            EventsFilter(
                type = FilterType.IN,
                filter = Filter.ContractIdFilter(ids),
            ),
        )
    }

    override fun excludeContracts(ids: List<ContractId>) {
        filters.add(
            EventsFilter(
                type = FilterType.OUT,
                filter = Filter.ContractIdFilter(ids),
            ),
        )
    }

    fun build(): List<EventsFilter> =
        filters.toList()
}
