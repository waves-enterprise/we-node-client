package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.DataEntry
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PublicKey
import com.wavesenterprise.sdk.node.domain.Timestamp
import com.wavesenterprise.sdk.node.domain.TxId
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractVersion
import com.wavesenterprise.sdk.node.domain.tx.CallContractTx

data class CallContractSignRequest(
    override val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val contractId: ContractId,
    val params: List<DataEntry>,
    val fee: Fee,
    val contractVersion: ContractVersion,
    val feeAssetId: FeeAssetId? = null,
    override val atomicBadge: AtomicBadge? = null,
) : AtomicInnerSignRequest<CallContractTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?): SignRequest<CallContractTx> = copy(password = password)
    override fun withAtomicBadge(atomicBadge: AtomicBadge?) =
        copy(atomicBadge = atomicBadge)

    companion object {
        @JvmStatic
        fun CallContractSignRequest.toTx(senderPublicKey: PublicKey) = CallContractTx(
            id = TxId.EMPTY,
            senderPublicKey = senderPublicKey,
            contractId = contractId,
            params = params,
            fee = fee,
            timestamp = Timestamp(System.currentTimeMillis()),
            contractVersion = contractVersion,
            feeAssetId = feeAssetId,
            atomicBadge = atomicBadge,
            proofs = listOf(),
            senderAddress = senderAddress,
            version = requireNotNull(version),
        )
    }
}
