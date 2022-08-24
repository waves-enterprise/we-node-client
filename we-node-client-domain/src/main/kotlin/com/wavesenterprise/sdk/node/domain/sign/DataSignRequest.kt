package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.FeeAssetId
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.PermitDataEntry
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.DataTx

data class DataSignRequest(
    val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val feeAssetId: FeeAssetId? = null,
    val author: Address,
    val data: List<PermitDataEntry>,
) : SignRequest<DataTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password) = copy(password = password)
}
