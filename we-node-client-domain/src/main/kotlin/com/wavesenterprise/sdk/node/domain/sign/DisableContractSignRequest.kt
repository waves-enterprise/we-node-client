package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.tx.DisableContractTx

data class DisableContractSignRequest(
    val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val contractId: ContractId,
    val feeAssetId: FeeAssetId? = null,
    override val atomicBadge: AtomicBadge? = null,
) : AtomicInnerSignRequest<DisableContractTx> {

    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?): SignRequest<DisableContractTx> = copy(password = password)

    override fun withAtomicBadge(atomicBadge: AtomicBadge?): DisableContractSignRequest =
        copy(atomicBadge = atomicBadge)
}
