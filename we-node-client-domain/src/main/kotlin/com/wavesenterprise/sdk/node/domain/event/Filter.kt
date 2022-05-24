package com.wavesenterprise.sdk.node.domain.event

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.contract.ContractId

sealed interface Filter {
    data class TxTypeFilter(val txTypes: List<TxType>) : Filter {
        constructor(vararg txTypes: TxType) : this(txTypes.toList())
    }

    data class ContractIdFilter(val contractIds: List<ContractId>) : Filter
}
