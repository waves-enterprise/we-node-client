package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PolicyDescription
import com.wavesenterprise.sdk.node.domain.PolicyName
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.tx.CreatePolicyTx

data class CreatePolicySignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val policyName: PolicyName,
    val recipients: List<Address>,
    val owners: List<Address>,
    val description: PolicyDescription,
    override val atomicBadge: AtomicBadge? = null,
) : AtomicInnerSignRequest<CreatePolicyTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?): SignRequest<CreatePolicyTx> = copy(password = password)
    override fun withAtomicBadge(atomicBadge: AtomicBadge?) =
        copy(atomicBadge = atomicBadge)

    companion object {
        @JvmStatic
        fun CreatePolicySignRequest.toTx(senderPublicKey: PublicKey) = CreatePolicyTx(
            id = TxId.EMPTY,
            senderPublicKey = senderPublicKey,
            policyName = policyName,
            description = description,
            recipients = recipients,
            owners = owners,
            timestamp = Timestamp(System.currentTimeMillis()),
            fee = fee,
            feeAssetId = feeAssetId,
            atomicBadge = atomicBadge,
            proofs = listOf(),
            senderAddress = senderAddress,
            version = requireNotNull(version),
        )
    }
}
