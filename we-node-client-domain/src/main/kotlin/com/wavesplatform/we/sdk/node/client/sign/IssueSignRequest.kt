package com.wavesplatform.we.sdk.node.client.sign

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Decimals
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.IssueTxDescription
import com.wavesplatform.we.sdk.node.client.IssueTxName
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.Quantity
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.IssueTx

data class IssueSignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val name: IssueTxName,
    val quantity: Quantity,
    val description: IssueTxDescription,
    val decimals: Decimals,
    val reissuable: Boolean,
) : SignRequest<IssueTx>
