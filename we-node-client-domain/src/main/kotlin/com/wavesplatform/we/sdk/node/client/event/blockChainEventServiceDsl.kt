package com.wavesplatform.we.sdk.node.client.event

import com.wavesplatform.we.sdk.node.client.ContractId
import com.wavesplatform.we.sdk.node.client.Signature
import com.wavesplatform.we.sdk.node.client.TxType

@DslMarker
annotation class BlockchainEventsDsl

@BlockchainEventsDsl
fun BlockchainEventsService.fromGenesis(filtersBuilder: EventsFilterContext.() -> Unit = {}) =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Genesis,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )

@BlockchainEventsDsl
fun BlockchainEventsService.fromBlock(signature: Signature, filtersBuilder: EventsFilterContext.() -> Unit = {}) =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.BlockSignature(signature),
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )

@BlockchainEventsDsl
fun BlockchainEventsService.fromCurrent(filtersBuilder: EventsFilterContext.() -> Unit = {}) =
    events(
        SubscribeOnRequest(
            startFrom = StartFrom.Current,
            filters = EventsFilterContextImpl().apply(filtersBuilder).build(),
        )
    )

@BlockchainEventsDsl
interface EventsFilterContext {
    fun includeTypes(types: List<TxType>)
    fun includeTypes(vararg types: TxType)
    fun excludeTypes(types: List<TxType>)
    fun excludeTypes(vararg types: TxType)
    fun includeContracts(ids: List<ContractId>)
    fun excludeContracts(ids: List<ContractId>)
}

internal class EventsFilterContextImpl : EventsFilterContext {
    private val filters: MutableList<EventsFilter> = mutableListOf()

    override fun includeTypes(types: List<TxType>) {
        filters.add(
            EventsFilter(
                type = FilterType.IN,
                filter = Filter.TxTypeFilter(types)
            )
        )
    }

    override fun includeTypes(vararg types: TxType) =
        includeTypes(types.toList())

    override fun excludeTypes(types: List<TxType>) {
        filters.add(
            EventsFilter(
                type = FilterType.OUT,
                filter = Filter.TxTypeFilter(types)
            )
        )
    }

    override fun excludeTypes(vararg types: TxType) =
        excludeTypes(types.toList())

    override fun includeContracts(ids: List<ContractId>) {
        filters.add(
            EventsFilter(
                type = FilterType.IN,
                filter = Filter.ContractIdFilter(ids)
            )
        )
    }

    override fun excludeContracts(ids: List<ContractId>) {
        filters.add(
            EventsFilter(
                type = FilterType.OUT,
                filter = Filter.ContractIdFilter(ids)
            )
        )
    }

    fun build(): List<EventsFilter> =
        filters.toList()
}
