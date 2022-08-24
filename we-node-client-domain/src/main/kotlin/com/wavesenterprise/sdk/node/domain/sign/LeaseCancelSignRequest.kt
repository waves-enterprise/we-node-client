package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.LeaseId
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.LeaseCancelTx

data class LeaseCancelSignRequest(
    val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val leaseId: LeaseId,
) : SignRequest<LeaseCancelTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password) = copy(password = password)
}
