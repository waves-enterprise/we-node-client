package com.wavesplatform.we.sdk.node.client.http.sign

import com.wavesplatform.we.sdk.node.client.TxType
import com.wavesplatform.we.sdk.node.client.http.tx.AtomicInnerTxDto
import com.wavesplatform.we.sdk.node.client.http.tx.AtomicInnerTxDto.Companion.toDto
import com.wavesplatform.we.sdk.node.client.http.tx.AtomicTxDto
import com.wavesplatform.we.sdk.node.client.sign.AtomicSignRequest

data class AtomicSignRequestDto(
    override val type: Int = TxType.ATOMIC.code,
    val version: Int?,
    val sender: String,
    val password: String?,
    val fee: Long,
    val txs: List<AtomicInnerTxDto>
) : SignRequestDto<AtomicTxDto> {
    companion object {
        @JvmStatic
        fun AtomicSignRequest.toDto(): AtomicSignRequestDto =
            AtomicSignRequestDto(
                version = version?.value,
                sender = senderAddress.asBase58String(),
                password = password?.value,
                fee = fee.value,
                txs = txs.map { it.toDto() },
            )
    }
}
