package com.wavesenterprise.sdk.node.domain.contract.keys

import com.wavesenterprise.sdk.node.domain.TxId

data class ContractKeysRequest(
    val contractId: TxId,
    val limit: Int,
    val offset: Int,
    val matches: String,
    val keysFilter: KeysFilter
)
