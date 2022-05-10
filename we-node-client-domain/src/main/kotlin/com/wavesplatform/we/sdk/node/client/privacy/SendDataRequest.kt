package com.wavesplatform.we.sdk.node.client.privacy

import com.wavesplatform.we.sdk.node.client.Address
import com.wavesplatform.we.sdk.node.client.AtomicBadge
import com.wavesplatform.we.sdk.node.client.Fee
import com.wavesplatform.we.sdk.node.client.FeeAssetId
import com.wavesplatform.we.sdk.node.client.Hash
import com.wavesplatform.we.sdk.node.client.Password
import com.wavesplatform.we.sdk.node.client.PolicyId
import com.wavesplatform.we.sdk.node.client.atomic.HasAtomicBadge

data class SendDataRequest(
    val senderAddress: Address,
    val policyId: PolicyId,
    val dataHash: Hash,
    val data: Data,
    val info: PolicyItemFileInfo,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    override val atomicBadge: AtomicBadge? = null,
    val password: Password? = null,
    val hash: Hash,
    val broadcastTx: Boolean,
) : HasAtomicBadge<SendDataRequest> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): SendDataRequest =
        copy(atomicBadge = atomicBadge)
}
