package com.wavesenterprise.sdk.node.domain.http.sign

import com.wavesenterprise.sdk.node.domain.TxType
import com.wavesenterprise.sdk.node.domain.http.TransferDto
import com.wavesenterprise.sdk.node.domain.http.TransferDto.Companion.toDto
import com.wavesenterprise.sdk.node.domain.http.tx.MassTransferTxDto
import com.wavesenterprise.sdk.node.domain.sign.MassTransferSignRequest

data class MassTransferSignRequestDto(
    override val type: Int = TxType.MASS_TRANSFER.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val transfers: List<TransferDto>,
) : SignRequestDto<MassTransferTxDto> {
    companion object {
        @JvmStatic
        fun MassTransferSignRequest.toDto(): MassTransferSignRequestDto =
            MassTransferSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                transfers = transfers.map { it.toDto() }
            )
    }
}
