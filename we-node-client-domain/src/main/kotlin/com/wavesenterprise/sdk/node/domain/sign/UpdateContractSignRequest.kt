package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ContractApiVersion
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.ValidationPolicy
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx

data class UpdateContractSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val contractId: ContractId,
    val image: ContractImage,
    val imageHash: ContractImageHash,
    val apiVersion: ContractApiVersion? = null,
    val validationPolicy: ValidationPolicy? = null,
    override val atomicBadge: AtomicBadge? = null,
) : AtomicInnerSignRequest<UpdateContractTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?): SignRequest<UpdateContractTx> = copy(password = password)
    override fun withAtomicBadge(atomicBadge: AtomicBadge?) =
        copy(atomicBadge = atomicBadge)

    companion object {
        @JvmStatic
        fun UpdateContractSignRequest.toTx(senderPublicKey: PublicKey) = UpdateContractTx(
            id = TxId.EMPTY,
            senderPublicKey = senderPublicKey,
            contractId = contractId,
            image = image,
            imageHash = imageHash,
            fee = fee,
            timestamp = Timestamp(System.currentTimeMillis()),
            feeAssetId = feeAssetId,
            atomicBadge = atomicBadge,
            validationPolicy = validationPolicy,
            apiVersion = apiVersion,
            proofs = listOf(),
            senderAddress = senderAddress,
            version = requireNotNull(version),
        )
    }
}
