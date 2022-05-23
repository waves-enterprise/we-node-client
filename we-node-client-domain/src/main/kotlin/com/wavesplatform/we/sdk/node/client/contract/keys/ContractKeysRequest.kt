package com.wavesplatform.we.sdk.node.client.contract.keys

import com.wavesplatform.we.sdk.node.client.TxId

data class ContractKeysRequest(
    val contractId: TxId,
    val limit: Int,
    val offset: Int,
    val matches: String,
    val keysFilter: KeysFilter
)
