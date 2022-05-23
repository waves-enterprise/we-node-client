package com.wavesplatform.we.sdk.node.client.contract.keys

import com.wavesplatform.we.sdk.node.client.TxId

data class ContractKeyRequest(
    val contractId: TxId,
    val key: String
)
