package com.wavesenterprise.sdk.node.domain.contract.keys

import com.wavesenterprise.sdk.node.domain.TxId

data class ContractKeyRequest(
    val contractId: TxId,
    val key: String
)
