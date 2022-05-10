package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.RoleConstants.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.GenesisPermitTxDto
import com.wavesplatform.we.sdk.node.client.sign.GenesisPermitSignRequest

data class GenesisPermitSignRequestDto(
    override val type: Int = TxType.GENESIS_PERMIT.code,
    val version: Int? = null,
    val sender: String,
    val password: String? = null,
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
