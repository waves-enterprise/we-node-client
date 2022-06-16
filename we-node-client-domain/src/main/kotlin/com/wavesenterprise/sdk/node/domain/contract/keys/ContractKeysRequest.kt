package com.wavesenterprise.sdk.node.domain.contract.keys

import com.wavesenterprise.sdk.node.domain.contract.ContractId

data class ContractKeysRequest(
    val contractId: ContractId,
    val keysFilter: KeysFilter,
    val limit: Int?,
    val offset: Int?,
    val matches: String? = null,
)
