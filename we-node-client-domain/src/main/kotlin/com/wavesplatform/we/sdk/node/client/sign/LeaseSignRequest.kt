package com.wavesplatform.we.sdk.node.client.sign

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Amount
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.LeaseTx

data class LeaseSignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val recipient: Address,
    val amount: Amount,
) : SignRequest<LeaseTx>
