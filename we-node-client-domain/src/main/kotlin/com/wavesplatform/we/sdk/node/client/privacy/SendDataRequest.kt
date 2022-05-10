package com.wavesplatform.we.sdk.node.client.privacy

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AtomicBadge
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.PolicyId

data class SendDataRequest(
    val senderAddress: Address,
    val policyId: PolicyId,
    val dataHash: Hash,
    val data: ByteArray,
    val info: PolicyItemFileInfo,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val atomicBadge: AtomicBadge? = null,
    val password: Password? = null,
    val hash: ByteArray,
    val broadcastTx: Boolean,
)
