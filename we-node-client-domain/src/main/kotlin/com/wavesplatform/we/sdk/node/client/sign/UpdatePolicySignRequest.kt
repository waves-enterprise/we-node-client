package com.wavesplatform.we.sdk.node.client.sign

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AtomicBadge
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.OpType
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.TxVersion
import com.wavesplatform.we.sdk.node.client.tx.UpdatePolicyTx

data class UpdatePolicySignRequest(
    val version: TxVersion? = null,
    val senderAddress: Address,
    val password: Password? = null,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val policyId: PolicyId,
    val opType: OpType,
    val recipients: List<Address>,
    val owners: List<Address>,
    val atomicBadge: AtomicBadge? = null,
) : SignRequest<UpdatePolicyTx>
