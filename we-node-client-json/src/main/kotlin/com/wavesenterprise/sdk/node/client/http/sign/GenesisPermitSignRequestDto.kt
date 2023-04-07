package com.wavesenterprise.sdk.node.client.http.sign

import com.wavesenterprise.sdk.node.client.http.RoleConstants.toDto
import com.wavesenterprise.sdk.node.client.http.tx.GenesisPermitTxDto
import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.sign.GenesisPermitSignRequest

data class GenesisPermitSignRequestDto(
    override val type: Int = TxType.GENESIS_PERMIT.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val target: String,
    val role: String,
) : SignRequestDto<GenesisPermitTxDto> {
    companion object {
        @JvmStatic
        fun GenesisPermitSignRequest.toDto(): GenesisPermitSignRequestDto =
            GenesisPermitSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                target = target.asBase58String(),
                role = role.toDto(),
            )
    }
}
