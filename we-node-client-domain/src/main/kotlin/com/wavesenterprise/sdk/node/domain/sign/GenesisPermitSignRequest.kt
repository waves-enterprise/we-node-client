package com.wavesenterprise.sdk.node.domain.sign

import com.wavesenterprise.sdk.node.domain.Address
import com.wavesenterprise.sdk.node.domain.Fee
import com.wavesenterprise.sdk.node.domain.Password
import com.wavesenterprise.sdk.node.domain.Role
import com.wavesenterprise.sdk.node.domain.TxVersion
import com.wavesenterprise.sdk.node.domain.tx.GenesisPermitTx

data class GenesisPermitSignRequest(
    val version: TxVersion? = null,
    override val senderAddress: Address,
    override val password: Password? = null,
    val fee: Fee,
    val target: Address,
    val role: Role,
) : SignRequest<GenesisPermitTx> {
    override fun withAddress(address: Address) = copy(senderAddress = address)

    override fun withPassword(password: Password) = copy(password = password)
}
