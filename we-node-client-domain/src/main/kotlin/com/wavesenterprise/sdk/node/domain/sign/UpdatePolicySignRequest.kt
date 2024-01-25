package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.OpType
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PolicyId
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.UpdatePolicyTx

data class UpdatePolicySignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val policyId: PolicyId,
    val opType: OpType,
    val recipients: List<Address>,
    val owners: List<Address>,
    override val atomicBadge: AtomicBadge? = null,
) : AtomicInnerSignRequest<UpdatePolicyTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?): SignRequest<UpdatePolicyTx> = copy(password = password)

    override fun withAtomicBadge(atomicBadge: AtomicBadge?): UpdatePolicySignRequest =
        copy(atomicBadge = atomicBadge)

    companion object {
        @JvmStatic
        fun UpdatePolicySignRequest.toTx(senderPublicKey: PublicKey) = UpdatePolicyTx(
            id = TxId.EMPTY,
            senderPublicKey = senderPublicKey,
            policyId = policyId,
            recipients = recipients,
            owners = owners,
            opType = opType,
            timestamp = Timestamp(System.currentTimeMillis()),
            fee = fee,
            feeAssetId = feeAssetId,
            atomicBadge = atomicBadge,
            senderAddress = senderAddress,
            version = requireNotNull(version),
        )
    }
}
