package com.wavesenterprise.sdk.node.domain.privacy

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Hash
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.atomic.HasMutableAtomicBadge

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
    val broadcastTx: Boolean,
    val version: TxVersion? = null,
) : HasMutableAtomicBadge<SendDataRequest> {
    override fun withAtomicBadge(atomicBadge: AtomicBadge?): SendDataRequest =
        copy(atomicBadge = atomicBadge).also {
            require(!broadcastTx) {
                "Atomic badge for immediately broadcasted SendDataRequest won't be used"
            }
        }
}
