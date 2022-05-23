package com.wavesplatform.we.sdk.node.client.event

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.contract.ContractId

sealed interface Filter {
    data class TxTypeFilter(val txTypes: List<TxType>) : Filter {
        constructor(vararg txTypes: TxType) : this(txTypes.toList())
    }

    data class ContractIdFilter(val contractIds: List<ContractId>) : Filter
}
