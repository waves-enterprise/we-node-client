package com.wavesenterprise.sdk.node.client.grpc.mapper.event

import com.wavesenterprise.protobuf.service.util.events.UtilEventsSubscribeOnRequest
import com.wavesenterprise.sdk.node.client.grpc.mapper.ContractIdMapper.byteString
import com.wavesenterprise.sdk.node.domain.event.EventsFilter
import com.wavesenterprise.sdk.node.domain.event.Filter
import com.wavesenterprise.sdk.node.domain.event.FilterType

object EventFilterMapper {

    @JvmStatic
    fun EventsFilter.dto(): UtilEventsSubscribeOnRequest.EventsFilter = dtoInternal(this)

    @JvmStatic
    internal fun dtoInternal(eventsFilter: EventsFilter): UtilEventsSubscribeOnRequest.EventsFilter =
        with(eventsFilter) {
            UtilEventsSubscribeOnRequest.EventsFilter.newBuilder()
                .setType(type)
                .setFilter(filter)
                .build()
        }

    private fun UtilEventsSubscribeOnRequest.EventsFilter.Builder.setType(type: FilterType) =
        when (type) {
            FilterType.IN -> setFilterIn(FILTER_IN)
            FilterType.OUT -> setFilterOut(FILTER_OUT)
        }

    private fun UtilEventsSubscribeOnRequest.EventsFilter.Builder.setFilter(filter: Filter) =
        when (filter) {
            is Filter.ContractIdFilter -> setContractIdFilter(
                UtilEventsSubscribeOnRequest.ContractIdFilter.newBuilder()
                    .addAllContractIds(filter.contractIds.map { it.byteString() })
                    .build()
            )

            is Filter.TxTypeFilter -> setTxTypeFilter(
                UtilEventsSubscribeOnRequest.TxTypeFilter.newBuilder()
                    .addAllTxTypes(filter.txTypes.map { it.code })
                    .build()
            )
        }

    private val FILTER_IN = UtilEventsSubscribeOnRequest.EventsFilter.FilterIn.newBuilder().build()
    private val FILTER_OUT = UtilEventsSubscribeOnRequest.EventsFilter.FilterOut.newBuilder().build()
}
