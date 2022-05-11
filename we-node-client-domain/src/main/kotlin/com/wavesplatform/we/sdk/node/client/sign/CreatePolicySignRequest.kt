package com.wavesplatform.we.sdk.node.client.sign

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.PolicyDescription
import com.wavesplatform.we.sdk.node.client.PolicyName
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.atomic.AtomicBadge
import com.wavesplatform.we.sdk.node.client.tx.CreatePolicyTx

data class CreatePolicySignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val policyName: PolicyName,
    val recipients: List<Address>,
    val owners: List<Address>,
    val description: PolicyDescription,
    val atomicBadge: AtomicBadge? = null,
) : SignRequest<CreatePolicyTx>
