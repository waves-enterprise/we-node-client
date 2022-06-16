package com.wavesenterprise.sdk.node.domain.contract.keys

import com.wavesenterprise.sdk.node.domain.contract.ContractId

data class ContractKeyRequest(
    val contractId: ContractId,
    val key: String,
)
