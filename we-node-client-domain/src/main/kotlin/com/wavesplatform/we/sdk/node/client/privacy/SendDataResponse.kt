package com.wavesplatform.we.sdk.node.client.privacy

import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.PolicyDataHashTx

data class SendDataResponse(
    val txVersion: TxVersion,
    val tx: PolicyDataHashTx,
)
