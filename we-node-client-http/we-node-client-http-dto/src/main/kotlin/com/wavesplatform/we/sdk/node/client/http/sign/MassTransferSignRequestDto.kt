package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.TransferDto
import com.wavesplatform.we.sdk.node.client.http.TransferDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.MassTransferTxDto
import com.wavesplatform.we.sdk.node.client.sign.MassTransferSignRequest

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
