package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.ContractApiVersion
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.ValidationPolicy
import com.wavesenterprise.sdk.node.domain.atomic.AtomicBadge
import com.wavesenterprise.sdk.node.domain.contract.ContractId
import com.wavesenterprise.sdk.node.domain.contract.ContractImage
import com.wavesenterprise.sdk.node.domain.contract.ContractImageHash
import com.wavesenterprise.sdk.node.domain.tx.UpdateContractTx

data class UpdateContractSignRequest(
    val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val contractId: ContractId,
    val image: ContractImage,
    val imageHash: ContractImageHash,
    val apiVersion: ContractApiVersion? = null,
    val validationPolicy: ValidationPolicy? = null,
    val atomicBadge: AtomicBadge? = null,
) : SignRequest<UpdateContractTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password?) = copy(password = password)
}
